import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;

public class Filehandler {
    public static void save(){
        Gson gson = new Gson();
        String json = gson.toJson(Game.Accounts);
        try(DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("saved_accounts")))){
            dos.writeUTF(json);
        }catch (IOException e){
            System.out.println("couldn't save");
        }
    }

    public static void load(){
        Gson gson = new Gson();
        try(DataInputStream dos = new DataInputStream(new BufferedInputStream(new FileInputStream("saved_accounts")))){
            String json = dos.readUTF();
            Game.Accounts = gson.fromJson(json,new TypeToken<ArrayList<Account>>(){}.getType());
        }catch (IOException e){
            System.out.println("couldn't load");
        }
    }
}
