package com.twelvemonkeys.io;

import com.twelvemonkeys.lang.DateUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class WriterOutputStream extends OutputStream {
   protected Writer writer;
   protected final WriterOutputStream.Decoder decoder;
   final ByteArrayOutputStream bufferStream = new FastByteArrayOutputStream(1024);
   private volatile boolean isFlushing = false;
   private static final boolean NIO_AVAILABLE = isNIOAvailable();

   private static boolean isNIOAvailable() {
      try {
         Class.forName("java.nio.charset.Charset");
         return true;
      } catch (Throwable var1) {
         return false;
      }
   }

   public WriterOutputStream(Writer var1, String var2) {
      this.writer = var1;
      this.decoder = getDecoder(var2);
   }

   public WriterOutputStream(Writer var1) {
      this(var1, null);
   }

   private static WriterOutputStream.Decoder getDecoder(String var0) {
      return (WriterOutputStream.Decoder)(NIO_AVAILABLE ? new WriterOutputStream.CharsetDecoder(var0) : new WriterOutputStream.StringDecoder(var0));
   }

   @Override
   public void close() throws IOException {
      this.flush();
      this.writer.close();
      this.writer = null;
   }

   @Override
   public void flush() throws IOException {
      this.flushBuffer();
      this.writer.flush();
   }

   @Override
   public final void write(byte[] var1) throws IOException {
      if (var1 == null) {
         throw new NullPointerException("bytes == null");
      } else {
         this.write(var1, 0, var1.length);
      }
   }

   @Override
   public final void write(byte[] var1, int var2, int var3) throws IOException {
      this.flushBuffer();
      this.decoder.decodeTo(this.writer, var1, var2, var3);
   }

   @Override
   public final void write(int var1) {
      this.bufferStream.write(var1);
   }

   private void flushBuffer() throws IOException {
      if (!this.isFlushing && this.bufferStream.size() > 0) {
         this.isFlushing = true;
         this.bufferStream.writeTo(this);
         this.bufferStream.reset();
         this.isFlushing = false;
      }
   }

   public static void main(String[] var0) throws IOException {
      int var1 = 1000000;
      byte[] var2 = "������ klashf lkash ljah lhaaklhghdfgu ksd".getBytes("UTF-8");
      PrintWriter var8 = new PrintWriter(new NullOutputStream());
      WriterOutputStream.StringDecoder var3 = new WriterOutputStream.StringDecoder("UTF-8");

      for (int var11 = 0; var11 < 10000; var11++) {
         var3.decodeTo(var8, var2, 0, var2.length);
      }

      long var4 = System.currentTimeMillis();

      for (int var21 = 0; var21 < var1; var21++) {
         var3.decodeTo(var8, var2, 0, var2.length);
      }

      long var6 = DateUtil.delta(var4);
      System.out.println("StringDecoder");
      System.out.println("time: " + var6);
      StringWriter var9 = new StringWriter();
      var3.decodeTo(var9, var2, 0, var2.length);
      String var10 = var9.toString();
      System.out.println("str: \"" + var10 + "\"");
      System.out.println("chars.length: " + var10.length());
      System.out.println();
      if (NIO_AVAILABLE) {
         WriterOutputStream.CharsetDecoder var16 = new WriterOutputStream.CharsetDecoder("UTF-8");

         for (int var22 = 0; var22 < 10000; var22++) {
            var16.decodeTo(var8, var2, 0, var2.length);
         }

         var4 = System.currentTimeMillis();

         for (int var23 = 0; var23 < var1; var23++) {
            var16.decodeTo(var8, var2, 0, var2.length);
         }

         var6 = DateUtil.delta(var4);
         System.out.println("CharsetDecoder");
         System.out.println("time: " + var6);
         var9 = new StringWriter();
         var16.decodeTo(var9, var2, 0, var2.length);
         var10 = var9.toString();
         System.out.println("str: \"" + var10 + "\"");
         System.out.println("chars.length: " + var10.length());
         System.out.println();
      }

      WriterOutputStream var24 = new WriterOutputStream(new PrintWriter(System.out), "UTF-8");
      var24.write(var2);
      var24.flush();
      System.out.println();

      for (byte var15 : var2) {
         var24.write(var15 & 255);
      }

      var24.flush();
   }

   private static final class CharsetDecoder implements WriterOutputStream.Decoder {
      final Charset mCharset;

      CharsetDecoder(String var1) {
         String var2 = var1 != null ? var1 : System.getProperty("file.encoding", "ISO-8859-1");
         this.mCharset = Charset.forName(var2);
      }

      @Override
      public void decodeTo(Writer var1, byte[] var2, int var3, int var4) throws IOException {
         CharBuffer var5 = this.mCharset.decode(ByteBuffer.wrap(var2, var3, var4));
         var1.write(var5.array(), 0, var5.length());
      }
   }

   private interface Decoder {
      void decodeTo(Writer var1, byte[] var2, int var3, int var4) throws IOException;
   }

   private static final class StringDecoder implements WriterOutputStream.Decoder {
      final String mCharset;

      StringDecoder(String var1) {
         this.mCharset = var1;
      }

      @Override
      public void decodeTo(Writer var1, byte[] var2, int var3, int var4) throws IOException {
         String var5 = this.mCharset == null ? new String(var2, var3, var4) : new String(var2, var3, var4, this.mCharset);
         var1.write(var5);
      }
   }
}
