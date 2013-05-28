/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.comm;

/**
 *
 * @author Rasmus
 */
public class SerialCommand {
    private char command;
    private String[] args;
    public static SerialCommand fromMessage(String message)
    {
        String[] parts = message.split(";");
        if (parts[0].length() != 1) return null;
        SerialCommand cmd = new SerialCommand();
        cmd.command = parts[0].charAt(0);
        cmd.args = new String[parts.length-1];
        for (int i = 0; i < cmd.args.length; i++)
            cmd.args[i] = parts[i+1];
        return cmd;
    }
    
    public char getCommand()
    {
        return command;
    }
    
    public String[] getArgs()
    {
        return args;
    }
    
    public int[] convertArgsToInt()
    {
        int[] result = new int[args.length];
        for (int i = 0; i < args.length; i++)
        {
            result[i] = Integer.parseInt(args[i]);
        }
        return result;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(command);
        for(String arg : args) {
            sb.append(";");
            sb.append(arg);
        }
        return sb.toString();
    }
    
}
