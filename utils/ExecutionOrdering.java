package clear.utils;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

/**
* This class is implementation of IMethodInterceptor interface, to sort the orderting of test
* @author ptt4kor
* @version 1.0
*/
public class ExecutionOrdering implements IMethodInterceptor  {

  @Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
		
		 Collections.sort(methods, new MyComparator());
		 return methods;
	}
	
	 public static class MyComparator implements Comparator<IMethodInstance> {
		 
		 @Override
		 public int compare(IMethodInstance o1, IMethodInstance o2) {
			// o1.getMethod().getConstructorOrMethod().get
			// o1.getInstance().toString()
		 return o1.getInstance().toString().compareTo(o2.getInstance().toString());
		 }
		  
		 }

}
