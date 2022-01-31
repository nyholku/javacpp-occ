package occ;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;
import org.bytedeco.javacpp.tools.*;

@Properties(//
		inherit = TKernel.class, //
		value = @Platform(link = { "TKG3d" }, //
				preload = { "" }, //
				include = { //
						"Geom_Geometry.hxx",//
				} //
		), target = "occ.TKG3d")

public class TKG3dConfig  implements InfoMapper {
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD, ElementType.PARAMETER })
	@Adapter(value = "occ_smart_ptr_adpter") // Refers to the C++ adapter class
	public @interface MySmartPtr { // Creates a Java annotation type to be used for smart pointers
		/** template type */
		String value() default "";
	}

	public void map(InfoMap infoMap) {
	}

}
