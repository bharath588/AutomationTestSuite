package features;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ManiFestFileGeneration {
	
	    public static int getUniqueNum(String mode)
	    {

	        Long secondsElapsed = System.currentTimeMillis();
	        System.out.println(secondsElapsed);
	        String text = Long.toString(secondsElapsed).substring(Math.max(0, 5));
	        System.out.println(text);
	        if(mode.equals("ST"))
	        {
	            text = text.substring(text.length() - 8, text.length());
	        	//text = text.substring(0,8);
	        }

	        if(mode.equals("HC"))
	        {
	            text = text.substring(text.length() - 9, text.length());
	        }

	        if(mode.equals("BW"))
	        {
	            text = text.substring(text.length() - 6, text.length());
	        }


	       System.out.println(text);
	       
	        return  Integer.parseInt(text);
	    }
	    
	    public static int lastnumber(String uniqueDeviceNumber){
	    
			String[] num1= uniqueDeviceNumber.split("");
			int oddsum=0, evensum=0;
			int actualResult=0;
			int length=num1.length;
			for(int i=0; i<length;i++)
			{
				if(i%2==0)
				{
					int j=Integer.parseInt(num1[i]);
					oddsum=oddsum+j;
					
				}else{
					int j=Integer.parseInt(num1[i]);
					evensum=evensum+j;
				
				}
			}
				
			int newNum=((oddsum*3)+evensum);
			if(newNum%10==0)
			{
				actualResult=0;
						
			}else{
				int newDigit=(newNum+9)/10;
				actualResult=((newDigit)*10)-newNum;
			}
	    	
	    	
			return actualResult;
	    	
	    }
	    
	    public static void createMainFestFile(HashMap<String,String> dataObj,String purchaseOrderId,int CaseNbr,int TrayNbr,String mode, int noOfDevicesRequired, String fileLocation)
	    {
	        try{
	            // create new file
	            String content = "This is the content to write into create file";
	            String path=fileLocation;
	            File file = new File(path);

	            // if file doesnt exists, then create it
	            if (!file.exists()) {
	                file.createNewFile();
	            }

	            FileWriter fw = new FileWriter(file.getAbsoluteFile());
	            BufferedWriter bw = new BufferedWriter(fw);
	            
	            int uniqueNumToGenerateDeviceNums = getUniqueNum(mode);
	            Date date = new Date(System.currentTimeMillis());
	            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	            String todayDate = formatter.format(date);
	            String uniqueNum;
	            int lastDigit;
	            int a=0;
	            
	            if(dataObj.get("TestCaseId").equals("POCR-057"))
	            {
		            for(int i=1;i<=noOfDevicesRequired-1;i++)
		            {
		            	uniqueNum = (uniqueNumToGenerateDeviceNums+i)+"0110";
		            	if(uniqueNum.length()==11)
		            	{
		            		uniqueNum=7+uniqueNum;
		            	}else if(uniqueNum.length()==13)
		            	{
		            		uniqueNum=uniqueNum.substring(1, uniqueNum.length());
		            	}else if(uniqueNum.length()==12)
		            	{
		            		
		            	}
		            	lastDigit=lastnumber(uniqueNum);            	
		            	
		                content =purchaseOrderId+","+i+","+CaseNbr+","+TrayNbr+
		                		","+todayDate+","+mode+","+uniqueNum+lastDigit+","+"06F00000,018";
		                a++;
		                if(a==1)
		                {
		                	bw.write(content);
			                bw.newLine();
			                bw.write(content);
			                bw.newLine();
		                }else
		                {
		                	bw.write(content);
			                bw.newLine();
		                }
		            }
	            	
	            }
	            else
	            {
	            for(int i=1;i<=noOfDevicesRequired;i++)
	            {
	            	uniqueNum = (uniqueNumToGenerateDeviceNums+i)+"0110";
	            	if(uniqueNum.length()==11)
	            	{
	            		uniqueNum=7+uniqueNum;
	            	}else if(uniqueNum.length()==13)
	            	{
	            		uniqueNum=uniqueNum.substring(1, uniqueNum.length());
	            	}else if(uniqueNum.length()==12)
	            	{
	            		
	            	}
	            	lastDigit=lastnumber(uniqueNum);            	
	            	
	                content =purchaseOrderId+","+i+","+CaseNbr+","+TrayNbr+
	                		","+todayDate+","+mode+","+uniqueNum+lastDigit+","+"06F00000,018";
	                bw.write(content);
	                bw.newLine();
	            }
	            }

	            bw.close();
	        }catch(Exception e){
	            System.out.println(e);
	        }
	    }

	    
	}
	

