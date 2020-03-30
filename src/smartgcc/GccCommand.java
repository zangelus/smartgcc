/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartgcc;

import java.io.IOException;

/**
 *
 * @author zange
 */
public class GccCommand {
    
    private String output;
    private String error;
    
    public String GetOutput(){
        return output;
    }
    
    public String GetError(){
        return error;
    }
     
    public int ExecutedCommand1(String[] command) {

        int processExitCode = -1;
        
        try {

            Runtime rt = Runtime.getRuntime();

            System.out.println(command[0]+ " " + 
                               command[1]+ " " +
                               command[2]+ " " +
                               command[3]+ " " );

            Process proc = rt.exec(command);
            StreamProcessReader errorStream = new StreamProcessReader(proc.getErrorStream(), "ERROR");
            StreamProcessReader outputStream = new StreamProcessReader(proc.getInputStream(), "OUTPUT");

            Thread a = new Thread(errorStream);
            Thread b = new Thread(outputStream);
            
            a.start();
            b.start();

            a.join();
            b.join();

            processExitCode = proc.waitFor();
            
            
            error = errorStream.getValue();
            output = outputStream.getValue();
            
            //System.out.println("Exit: " + processExitCode);
            //System.out.println("Error: " + s1);
            //System.out.println("Output: " + s2);

        } 
        catch (IOException | InterruptedException t) {
            System.out.println("Error: " + t);
        }
        
        return processExitCode;
    }
}
