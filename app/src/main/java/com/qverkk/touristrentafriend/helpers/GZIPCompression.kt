package com.qverkk.touristrentafriend.helpers

import org.apache.commons.io.IOUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.StringWriter
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class GZIPCompression {

    companion object {
        fun compress(str: String): ByteArray {
            val baos = ByteArrayOutputStream()
            val gzipOutput = GZIPOutputStream(baos)
            gzipOutput.write(str.toByteArray(Charsets.UTF_8))
            gzipOutput.close()
            return baos.toByteArray()
        }

        fun decompress(bytes: ByteArray): String {
            val gzipInput = GZIPInputStream(ByteArrayInputStream(bytes))
            val stringWriter = StringWriter()
            IOUtils.copy(gzipInput, stringWriter, Charsets.UTF_8)
            return stringWriter.toString()
        }
    }
}