import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String lWord = "";
    String[] sList = new String[50];
    int sIndex = 0;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Last Added Word: ", lWord);
        } else if (url.getPath().equals("/list")) {
            String retur = "";
            for (int i = 0; i<sList.length-1; i++){
                retur += sList[i] + ", ";
            }
            retur += sList[sList.length-1];
            return "List of strings: " + retur;
        } else if (url.getPath().contains("/add")){
            String[] parameters = url.getQuery().split("=");
            lWord = parameters[1];
            sList[sIndex] = lWord;
            sIndex++;
            return lWord + " is now added.";
        } else {
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("")) {
                    String searc = parameters[1];
                    String retur = "";
                    if (sList[0]==null){
                        return "No words were added";
                    }
                    for (int i = 0; i<sList.length-1; i++){
                        
                        if (sList[i].indexOf(searc)>=0) {
                            retur += sList[i] + ", ";
                        }
                        if (sList[i+1]==null){
                            i = sList.length+1;
                        }
                    }
                    return retur.substring(0, retur.length()-2);
                }
            }
            return "Page broke";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
