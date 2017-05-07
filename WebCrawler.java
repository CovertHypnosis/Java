import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Webcrawler class which finds all the links on the page
public class WebCrawler {
    public static void main(String[] args) {
        // get website address from the user
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a URL: ");
        String url = input.nextLine();
        // create list and call crawler method
        List<String> arr = crawler(url);
        // write list to the file
        writeToFile(arr);
        input.close();
    }


    // this method searches all the links from submitted string
    public static List<String> crawler(String startingURL) {
        // list of url-s
        List<String> listOfURLs = new ArrayList<>();

        try {
            // open submitted string as url stream
            URL url = new URL(startingURL);
            Scanner input = new Scanner(url.openStream());

            // read from the url till the end
            while (input.hasNext()) {
                String line = input.next();
                // link is final parsed link, firstworld is first world of the link(http,https)
                String link, firstWord;
                // call getfirst method
                firstWord = getFirstWord(line);
                if (firstWord != null) {
                    // check if link line contains double or single quote and call parseLink method
                    if (line.contains("\""))
                        link = parseLink('\"', firstWord, line);
                    else
                        link = parseLink('\'', firstWord, line);


                    // if link is already in list, dont add it again
                    if (!listOfURLs.contains(link))
                        listOfURLs.add(link);
                }
            }
        } catch (Exception ex) {
            System.out.println("Sorry there was some technical issue while getting links.");
        }

        // return list full of links found on that website
        return listOfURLs;
    }

    // this method parses line and returns link
    public static String parseLink(char filter, String firstWord, String line) {
        // start is index of first world(http, https, www)
        int start = line.indexOf(firstWord);
        // end is index of first single or double quote
        int end = line.indexOf(filter, start);
        if (end <= 0) end = line.length() - 1;

        return line.substring(start, end);
    }


    // this method gets the first world of the link string
    public static String getFirstWord(String line) {
        if (line.contains("http://"))
            return "http://";

        if (line.contains("https://"))
            return "https://";

        if (line.contains("www."))
            return "www";

        return null;
    }

    // this method writes all the websites to the file
    public static void writeToFile(List<String> arr) {
        try {
            PrintWriter writer = new PrintWriter("Links.txt", "utf-8");
            int count = 1;
            for (String str: arr) {
                writer.println(count + ") " + str);
                count++;
            }
            writer.close();
            System.out.println("Written Succesfully");
        } catch (IOException e) {
            System.out.println("Sorry there was some technical issue while writing links to file.");
        }
    }
}
