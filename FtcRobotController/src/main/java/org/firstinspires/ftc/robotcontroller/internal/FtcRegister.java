package org.firstinspires.ftc.robotcontroller.internal;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.reflections.Reflections;
import org.firstinspires.ftc.teamcode.competition2017.*;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class FtcRegister {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        console_App(args);
    }

    /**
     * console_App
     * @param args meant to represent the args from the main method. This is the console version of the FTC App Controller
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void console_App(String[] args) throws InvocationTargetException, IllegalAccessException {
        // The annotation type to access, either Autonomous or TeleOp
        Class<? extends Annotation> opType = null;
        // The console-based answering system
        int answer = 0;
        // Pre-declared loop control for re-asking a question or for asking similar questions
        boolean repeat = true;
        // a Scanner for user input and control
        Scanner reader = new Scanner(System.in);
        // Ask the initial question that will either set opType to an Annotation class or exit the program
        while(repeat) {
            System.out.println("Would you like to run a TeleOp(1), Autonomous(2), or exit(-1)?");
            answer = reader.nextInt();
            switch (answer) {
                case 1:
                    opType = TeleOp.class;
                    repeat = false; // end the loop to continue to next set of questions
                    break;
                case 2:
                    opType = Autonomous.class;
                    repeat = false; // end the loop to continue to next set of questions
                    break;
                case -1:
                    return;
                default:
                    System.out.println("\nWould you like to run a TeleOp(1), Autonomous(2), or exit(-1)?");
                    repeat = true; // redundant line to signify that the loop keeps repeating until an answer is given
            }
        }
        // classList will hold a list of OpModes
        ArrayList<Class<? extends OpMode>> classList = new ArrayList<>();
        // counter for the user output. this for loop prints out the user's options using the counter
        int counter = 0;
        // loop through every class that has annotations of the class of opType
        for(Class<? extends OpMode> newClass : getAnnotatedClass(opType)){
            // increment counter first so that the displayed digits start at 1 (not 0)
            counter++;

            // Gets the first annotation of class type opType. Should be fine as long as opmodes don't contain doubles
            Annotation annotation = newClass.getAnnotation(opType);
            // Create an Object placeholder for any kind of Annotated attribute (which is retrieved by means of Method)
            Object value = getAnnotatedValue(annotation, "name");

            System.out.println( value + " ("+counter+")");
            // System.out.println(newClass.getSimpleName() + " ("+counter+")"); // Old System of printing class names
            classList.add(newClass); // add the OpMode to the opmode ArrayList
        }
        repeat = true;
        OpMode opMode;

        while(repeat) {
            System.out.println("Would you like to exit(-1), or choose an OpMode:");

            try {
                answer = reader.nextInt();
                if (answer >= 1 && answer <= classList.size()){
                    opMode = classList.get(answer-1).newInstance();
                    console_runOpMode(opMode);
                    repeat = false;
                } else {
                    switch (answer) {
                        case -1:
                            return;
                        default:
                            System.out.println("\nWould you like to run a TeleOp(1), Autonomous(2), or exit(-1)?");
                    }
                }

            } catch (Exception e){}
        }

    }

    /**
     * console_runOpMode
     * accepts an OpMode subclass and then executes it in a fashion of the FTC Robot Controller
     * @param opMode a subType of the OpMode class that is to be run, console-style
     */
    public static void console_runOpMode(OpMode opMode) {
        boolean repeat = true;
        Scanner reader = new Scanner(System.in);
        int answer;
        int counter = 0;
        System.out.println("Do you want to Initialize (1)? Exit (-1)?");
        while(repeat){
            try {

                answer = reader.nextInt();
                if(answer == 1 && counter == 0){
                    opMode.init();
                    counter++;
                    System.out.println("Do you want to run the Loop(1)? Just for 30 secs(2)? Exit(-1)?");
                } else if (answer == 1 && counter == 1){
                    opMode.start();
                    counter++;
                    opMode.loop();
                    repeat = false;
                }
            } catch (Exception e){}
        }
    }

    /**
     * getAnnotatedClass
     * It is an overloaded version that will only look at classes that do not have the Disabled annotation
     *
     * @param noteType an Annotation class like @see TeleOp or @see Autonomous
     * @return an ArrayList that contains the OpMode subclasses that contain the given Annotation class type
     */
    public static ArrayList<Class<? extends OpMode>> getAnnotatedClass(Class<? extends Annotation> noteType){
        return getAnnotatedClass(noteType,true);
    }

    /**
     * getAnnotatedClass
     * Obtain subclasses of OpMode that contain the noteType class type of Annotation
     * @param noteType an Annotation class like @see TeleOp or @see Autonomous
     * @param isEnabled when true, ignores subTypes of OpMode that have the Disabled Annotation. when false, includes them.
     * @return an ArrayList that contains the OpMode subclasses that contain the given Annotation class type
     */
    public static ArrayList<Class<? extends OpMode>> getAnnotatedClass(Class noteType, boolean isEnabled){
        Iterator<Class<? extends OpMode>> classIterate;
        if(isEnabled)
            classIterate = getEnabled().iterator();
        else
            classIterate = getIterator();

        ArrayList<Class<? extends OpMode>> classList = new ArrayList<>();

        while(classIterate.hasNext()){
            Class<? extends OpMode> newClass = classIterate.next();
            boolean isNoted= false;
            for( Annotation note : newClass.getDeclaredAnnotations()){
                if (note.annotationType() == noteType) isNoted=true;
            }
            if(isNoted) classList.add(newClass);
        }
        return classList;
    }

    /**
     * getEnabled
     * @return an ArrayList of subTypes of OpMode that do not contain the Disabled Annotation
     */
    public static ArrayList<Class<? extends OpMode>> getEnabled(){
        Iterator<Class<? extends OpMode>> classIterate = getIterator();
        ArrayList<Class<? extends OpMode>> classList = new ArrayList<>();
        while(classIterate.hasNext()){
            Class<? extends OpMode> newClass = classIterate.next();
            boolean isDisabled = false;
            for( Annotation note : newClass.getDeclaredAnnotations()){
                if (note.annotationType() == com.qualcomm.robotcore.eventloop.opmode.Disabled.class) isDisabled=true;
            }
            if(!isDisabled) classList.add(newClass);
        }
        return classList;
    }

    /**
     * getAnnotatedValue
     * Retrieve a specific value/attribute by its field name from a given annotation
     *
     * @param annotation the annotation to look through
     * @param attributeName name of the attribute to get from the annotation
     * @return a generic Object type of the value of the annotation's attribute
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object getAnnotatedValue(Annotation annotation, String attributeName) throws InvocationTargetException, IllegalAccessException {
        Object value = new Object();
        // Iterate through the "methods" of the annotation.  The values are in the form of name() or group()
        for (Method method : annotation.getClass().getDeclaredMethods()) {
            // Get the value from the annotation if the method's name is "name"
            if(method.getName().equals(attributeName)){
                value = method.invoke(annotation, (Object[]) null);
                break;
            }
        }
        return value;
    }

    /**
     * test_reflections
     * Used to test the getIterator method. Will print out all of the long names of the subclasses of OpMode,
     * that are located in the TeamCode project folder
     */
    public static void test_reflections(){
        Iterator<Class<? extends OpMode>> classIterate = getIterator();
        while(classIterate.hasNext()){
            Class<? extends OpMode> newClass = classIterate.next();
        // System.out.println(classIterate.next().getSimpleName());
            System.out.println(classIterate.next().getName());
            for (Annotation annotation : newClass.getAnnotations()) {
                System.out.println(annotation.toString());
            }
        }
    }
    /**
     * getIterator
     * Uses the Reflections library to get a Set of subclasses of OpMode. The Set is then returned as an Iterator for usage.
     * @return an Iterator of the org.firstinspires.ftc package that contains the subclasses of OpMode
     */
    public static Iterator<Class<? extends OpMode>> getIterator(){
        String packageName = "org.firstinspires.ftc";
        //         Reflections reflections = new Reflections(ClasspathHelper.forPackage("my.package.prefix"),
//                    new SubTypesScanner());
//        Reflections reflections = new Reflections("org.firstinspires.ftc.teamcode.competition2017");
        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName))));

        Set<Class<? extends OpMode>> classSet =  reflections.getSubTypesOf(OpMode.class);
//        Object[] classArr = (Object[])classSet.toArray();
//        for (Object op : classArr){
//            System.out.println(op.getClass());
//        }
        Iterator<Class<? extends OpMode>> classIterate = classSet.iterator();
        return classIterate;
    }
}
