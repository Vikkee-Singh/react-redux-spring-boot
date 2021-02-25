package com.vapl.dialer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Util {
	
	public static String countryCodeURL = "http://api.ipinfodb.com/v3/ip-country/";
	public static String countryCodeKey = "a07c2ed45e9f385c4192279ea6316908c6a7cd8d12f85ad0264768a593c1c368";
	public static String getDate()
	{
		SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date=new java.util.Date();
		String strDate = dateFormatSQL.format(date);
		return strDate;
	}
	
	public static String getOnlyDate()
	{
		SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date=new java.util.Date();
		String strDate = dateFormatSQL.format(date);
		return strDate;
	}
	
	public static String getTimeStamp()
	{
		SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		java.util.Date date=new java.util.Date();
		String strDate = dateFormatSQL.format(date);
		System.out.println(strDate);
		return strDate;
	}
	
	public static String compareDates(String currentDate, String scheduleDate) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(currentDate);
        Date date2 = sdf.parse(scheduleDate);

        System.out.println("current date : " + sdf.format(date1));
        System.out.println("schedule date : " + sdf.format(date2));

        if (date1.compareTo(date2) == 0) {
            System.out.println("Date1 is equal to Date2");
            return "1";
        }
        else
        {
        	return "0";
        }
	}
	
	public static void modifyWidgetTemplate(String oldFilePath, String newFilePath, String newFileName, String oldString, String newString) throws IOException
	{
		File newFile = new File(newFilePath);
		String[] oldStrArr = oldString.split("#");
		String[] newStrArr = newString.split("#");
        if (!newFile.exists()) {
            if (newFile.mkdirs()) {
                System.out.println(newFile+" created!");
            } else {
                System.out.println("Failed to create directories!");
            }
        }
		for (String fn : new String[]{oldFilePath}) {
            String s = new String(Files.readAllBytes(Paths.get(fn)));
            s = s.replace(oldStrArr[0], newStrArr[0]);
            s = s.replace(oldStrArr[1], newStrArr[1]);
            s = s.replace(oldStrArr[2], newStrArr[2]);
            s = s.replace(oldStrArr[3], newStrArr[3]);
            try (FileWriter fw = new FileWriter(newFile+"/"+newFileName)) {
                fw.write(s);
                fw.close();
            }
        }
	}
	public static void modifyFile(String oldFilePath, String newFilePath, String oldString, String newString)
    {
        File fileToBeModified = new File(oldFilePath);
        File newFile = new File(newFilePath);
         
        String oldContent = "";
         
        BufferedReader reader = null;
         
        FileWriter writer = null;
         
        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));
             
            //Reading all the lines of input text file into oldContent
             
            String line = reader.readLine();
             
            while (line != null) 
            {
                oldContent = oldContent + line + System.lineSeparator();
                 
                line = reader.readLine();
            }
             
            //Replacing oldString with newString in the oldContent
             
            String newContent = oldContent.replaceAll(oldString, newString);
             
            //Rewriting the input text file with newContent
             
            writer = new FileWriter(newFile);
             
            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //Closing the resources
                 
                reader.close();
                 
                writer.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
	
	public static String changeDateTime(String dt, String timezone) throws ParseException {
        SimpleDateFormat sdfOriginal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfOriginal.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date1 = sdfOriginal.parse(dt);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        return sdf.format(calendar.getTime());
    }
	
	/*void sendEmailWithAttachment() throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("1@gmail.com");

        helper.setSubject("Testing from Spring Boot");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Check attachment for image!</h1>", true);

        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);

    }*/
}
