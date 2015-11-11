/**
 * Created by Sridhar Easwaran on 8/18/2015.
 */

package com.sri.soapui.listener.dataExtract;

        import com.eviware.soapui.SoapUI;
        import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep;
        import com.eviware.soapui.impl.wsdl.teststeps.WsdlRunTestCaseTestStep;
        import com.eviware.soapui.model.support.TestRunListenerAdapter;
        import com.eviware.soapui.model.testsuite.TestCaseRunContext;
        import com.eviware.soapui.model.testsuite.TestCaseRunner;
        import com.eviware.soapui.model.testsuite.TestStep;
        import com.eviware.soapui.model.testsuite.TestStepResult;
        import com.eviware.soapui.model.iface.MessageExchange;
        import com.eviware.soapui.model.ModelItem;
        import com.eviware.soapui.model.iface.Attachment;
        import com.eviware.soapui.model.iface.Operation;
        import com.eviware.soapui.model.iface.Response;
        import com.eviware.soapui.model.testsuite.ResultContainer;
        import com.eviware.soapui.support.types.StringToStringMap;
        import com.eviware.soapui.support.types.StringToStringsMap;

        import java.io.*;
        import java.text.SimpleDateFormat;
        import java.util.Date;

/*public class Test extends WsdlRunTestCaseTestStep{


}*/

public class ReportListener extends RestTestRequestStep,WsdlRunTestCaseTestStep {}

  public void afterStep(TestCaseRunner testRunner, TestCaseRunContext runContext, TestStepResult result){

      Date date=new Date();
      SimpleDateFormat df=new SimpleDateFormat("MMM-dd 'at' hh:mm:ss");
      String time=df.format(date);

      String step_name=result.getTestStep().getName().toString();
	  step_name=goodString(step_name);

      String case_name=testRunner.getTestCase().getName().toString();
	  case_name=goodString(case_name);
	  
      String suite_name=testRunner.getTestCase().getTestSuite().getName().toString();
	  suite_name=goodString(suite_name);
	  
      String project_name=testRunner.getTestCase().getTestSuite().getProject().getName().toString();
	  project_name=goodString(project_name);

      String filename=step_name+" _@_ "+time+".txt";
      filename=goodString(filename);

      String home=System.getProperty("user.home");

      String subdir="\\SoapUI Test Data\\Test Execution Data\\"+project_name+"\\"+suite_name+"\\"+case_name;

      File path=new File(home,subdir);

      if(!path.exists()){
          path.mkdirs();
      }

	  try{
		  File file =new File(path,filename);
    		
    		//if file doesnt exists, then create it
    		if(!file.exists()){
    			file.createNewFile();
    		}

          FileWriter fileWritter = new FileWriter(file,true);
    	    BufferedWriter bw = new BufferedWriter(fileWritter);
          PrintWriter pw = new PrintWriter(bw);

          pw.println();
          pw.write("Project Name   :   " + suite_name);
          pw.println();
          pw.write("  Suite Name   :   " + suite_name);
          pw.println();
          pw.write("   Case Name   :   " + case_name);
          pw.println();
          pw.write("   Step Name   :   " + step_name);
          pw.println();
          pw.println();
          pw.println();
          result.writeTo(pw);

          pw.close();
          SoapUI.log("Test data saved as : "+filename);
				
	  }
   catch (IOException e){
      SoapUI.log("Test data not Saved, due to some evil spirit !! wooooow ;) ");
}



  }
  
  public String goodString(String s){
	  
		String name=s;
		name=name.replaceAll("[^a-zA-Z0-9_ #@.-]","_");

		return name;
		
}

}


