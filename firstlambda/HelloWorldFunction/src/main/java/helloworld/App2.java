package helloworld;

import com.amazonaws.services.lambda.runtime.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class App2 {

    private double instanceVar = Math.random();
    private static double staticVar = Math.random();

    public App2() {
        System.out.println("Inside Constructor");
    }

    static {
        System.out.println("Static Block Executed.");
    }

    // Asynchronous call
    /*
        if we run multiple time this lambda function static and instance (Global Variable) variable value will not change each time only at 1st time.
        but local variable value gets changed each and every execution.
    */
    public void coldStartBasic() {

        double localVar = Math.random();
        System.out.println("instanceVar - {} staticVar - {} localVar - {} " + instanceVar + " " + staticVar + " " + localVar);

    }

    public void getOutPutStream(InputStream input, OutputStream output, Context context) throws IOException, InterruptedException {

        context.getLogger().log("time in ms remaining -> " + context.getRemainingTimeInMillis() + " <- ,");
        context.getLogger().log("-> " + context.getLogGroupName() + " log-group   <- ,");
        context.getLogger().log(" ->  default timeout is 3 sec. <- , ");
        context.getLogger().log(" ->  max timeout of a lambda function is 15 min. <- ,");
        context.getLogger().log("Environment variable API-URL -> " + System.getenv("orderServiceUrl"));

        //    Thread.sleep(3000);
        int data;
        while ((data = input.read()) != -1) {
            output.write(Character.toLowerCase(data));
        }

    }

}
