
import occ.TKernel.*;

public class OccDowncastDemo {
	public static void main(String[] args) {

		System.out.println("Demo that:");
		System.out.println("- creates an Example_Base instance");
		System.out.println("- creates an Example_Derived instance");
		System.out.println("- passes it as Example_Base instance to a C++ API overriden virtual function");
		System.out.println("- gets it back as nominally Example_Base instance");
		System.out.println("- prints the real Java class name");
		System.out.println("- prints the C++  class name");
		System.out.println("- check it is instanceof Example_Derived");
		System.out.println("- castit to Example_Derived");
		Example_Base base = new Example_Derived();
		System.out.println("base java class name  = " + base.getClass().getCanonicalName());
		System.out.println("base occ dynamic name = " + base.DynamicType().Name().getString());
		Example_Base ret = base.foobar(new Example_Derived());
		System.out.println("ret java class name   = " + ret.getClass().getCanonicalName());
		System.out.println("ret occ dynamic name  = " + ret.DynamicType().Name().getString());
		System.out.println("ret instanceof        = " + (ret instanceof Example_Derived));
		Example_Derived derived = (Example_Derived)ret;
		System.out.println("(Example_Derived)ret  = " +"ok");
		System.out.println("Done.");

	}

}
