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
import java.util.*;

public class FtcRegister {
    public static void main(String[] args){
        for(Class<? extends OpMode> newClass : getAnnotatedClass(Autonomous.class)){
            System.out.println(newClass.getName());
        }
    }
    public static ArrayList<Class<? extends OpMode>> getAnnotatedClass(Class noteType){
        Iterator<Class<? extends OpMode>> classIterate = getIterator();
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
    public static ArrayList<Class<? extends OpMode>> getUnDisabled(){
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
     * Uses the Reflections library to get a Set of subclasses of OpMode. The Set is then returned as an Iterator fro usage.
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
