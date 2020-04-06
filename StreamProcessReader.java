/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartgcc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author zange
 */
public class StreamProcessReader implements Runnable {

    private volatile StringBuilder sb = new StringBuilder();
    
    InputStream is;
    String msg;

    StreamProcessReader(InputStream is, String type)
    {
        this.is = is;
        this.msg = type;
    }
    @Override
    public void run() {
        
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                //System.out.println(msg + ">" + line);
                sb.append(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public String getValue() {
         return sb.toString();
     }
    
}
