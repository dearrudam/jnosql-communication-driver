/*
 *  Copyright (c) 2022 Contributors to the Eclipse Foundation
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the Eclipse Public License v1.0
 *   and Apache License v2.0 which accompanies this distribution.
 *   The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *   and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 *   You may elect to redistribute this code under either of these licenses.
 *
 *   Contributors:
 *
 *   Jesse Gallagher
 */
package org.eclipse.jnosql.communication.driver;

import org.eclipse.jnosql.communication.driver.attachment.EntityAttachment;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Contains tests to handle attachment representations.
 * 
 * @since 0.0.9
 */
@SuppressWarnings("nls")
public class AttachmentTest {
    private static final byte[] testData = "hello".getBytes();
    private static final String contentType = "text/plain";
    
    @Test
    public void testPathAttachment() throws IOException {
        Path tempFile = Files.createTempFile("jnosql-test", ".txt");
        try {
            try(OutputStream os = Files.newOutputStream(tempFile)) {
                os.write(testData);
            }
            
            EntityAttachment att = EntityAttachment.of(tempFile);
            checkAttachment(att, tempFile.getFileName().toString(), contentType, Files.getLastModifiedTime(tempFile).toMillis());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
    
    @Test
    public void testFileAttachment() throws IOException {
        Path tempFile = Files.createTempFile("jnosql-test", ".txt");
        try {
            try(OutputStream os = Files.newOutputStream(tempFile)) {
                os.write(testData);
            }
            
            EntityAttachment att = EntityAttachment.of(tempFile.toFile());
            checkAttachment(att, tempFile.getFileName().toString(), contentType, Files.getLastModifiedTime(tempFile).toMillis());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
    
    @Test
    public void testMemoryAttachment() throws IOException {
        String name = "testfile.txt";
        long mod = System.currentTimeMillis();
        
        EntityAttachment att = EntityAttachment.of(name, mod, contentType, testData);
        checkAttachment(att, name, contentType, mod);
    }
    
    private void checkAttachment(EntityAttachment att, String name, String contentType, long lastModified) throws IOException {
        assertEquals(name, att.getName());
        assertEquals(contentType, att.getContentType());
        assertEquals(testData.length, att.getLength());
        assertEquals(lastModified, att.getLastModified());
        
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try(InputStream is = att.getData()) {
            copyStream(is, data, 128);
        }
        
        assertArrayEquals(testData, data.toByteArray());
    }
    
     public static long copyStream(InputStream is, OutputStream os, int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        long totalBytes = 0;
        int readBytes;
        while( (readBytes = is.read(buffer))>0 ) {
            os.write(buffer, 0, readBytes);
            totalBytes += readBytes;
        }
        return totalBytes;
    }
}
