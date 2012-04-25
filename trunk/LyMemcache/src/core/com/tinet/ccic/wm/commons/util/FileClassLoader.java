package com.tinet.ccic.wm.commons.util;

import java.io.ByteArrayOutputStream;

import java.io.IOException;

import java.io.InputStream;

import java.lang.reflect.Method;

import java.net.URL;
/**
 * jar包文件类加载器。
 *<p>
 * 文件名： FileClassLoader.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @author 何治
 * @author 安静波
 * @since 1.0
 * @version 1.0
 */

public class FileClassLoader extends ClassLoader

{

    /**

     * @param urls

     */

    private  String drive;

    private  String fileType = ".class";


 


    public FileClassLoader(String jarFile) {

        drive=jarFile;

    }

    /**
     * 寻找目标类方法。
     * @param name 类名。
     * @return 返回找到的类。
     * @throws ClassNotFoundException 在目标类找不到时抛出该异常。
     */
     public Class findClass(String name)throws ClassNotFoundException

     {

        Class resultClass=null;

        String pathName=name.replace('.','/');

         byte[] data = loadClassData(pathName);

         resultClass=defineClass(name, data, 0, data.length);

        return resultClass;

     }

     /**
      * 加载类数据方法。
      * @param name 类名。
      * @return 返回请求的类内容。
      * @throws ClassNotFoundException 当类找不到时抛出该异常。
      */
     public byte[] loadClassData(String name)throws ClassNotFoundException

     {
         InputStream fis = null;

        ByteArrayOutputStream baos=null;

         byte[] data = null;

         try

         {

            URL url=new URL("jar:file:/"+drive+"!/"+name+".class");

             fis = url.openStream();

             baos = new ByteArrayOutputStream();

             int ch = 0;

             while ((ch = fis.read()) != -1)

             {

                 baos.write(ch);               //如果文件有加密,可在这进行解密,处理完成后返回正常格式的class字节数组         

             }

             data = baos.toByteArray();

         } catch (IOException e){

             throw new ClassNotFoundException("在"+drive+"文件中找不到"+"类"+name);

         }finally{

            try{

                if(baos!=null){

                    baos.close();

                }

                if(fis!=null){

                    fis.close();

                }

            }catch(Exception e){

            }

         }

        return data;

     }   

 

     public static void main(String[] args) throws Exception

     {

        FileClassLoader loader = new FileClassLoader("D:/TestDir/t.jar");

        Class objClass = loader.loadClass("com.TestClassLoader");

        Class objClass2 = loader.loadClass("com.TestClassLoaderUse");

        Object obj = objClass.newInstance();

        //通过reflect执行函数..doit(String a);

        Method method=objClass.getMethod("doit",new Class[]{String.class});

        System.out.println("method doit() return:"+method.invoke(obj,new Object[]{"J"}));

        System.out.println(objClass.getName());

        System.out.println(objClass.getClassLoader());               

      }

 }

