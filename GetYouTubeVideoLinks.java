package getyoutubevideolinks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Get URL links of all your uploaded YouTube videos.
 *
 * @author şamil
 */
public class GetYouTubeVideoLinks {

    final public static String LINK_START = "https://www.youtube.com/watch?v=";

    public static void main(String[] args) throws MalformedURLException, IOException {
        //URL ur = new URL("http://www.google.com/");
        /*URL ur = new URL("https://www.youtube.com/my_videos?o=U&pi=1");
         HttpURLConnection yc = (HttpURLConnection) ur.openConnection();
         try (BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()))) {
         String inputLine;
         while ((inputLine = in.readLine()) != null) {
         System.out.println(inputLine);
         }
         }*/
        List<String> htmlFileContents = readHTMLFiles("./htmlFiles/");
        List<String> allVideoLinks = new ArrayList<>();
        htmlFileContents.stream().forEach((htmlFileContent) -> {
            Set<String> videoLinks = getVideoLinks(htmlFileContent);            
            if (!videoLinks.isEmpty()) {
                allVideoLinks.addAll(videoLinks);
            }
        });
        writeToFile("allVideoLinks.txt", allVideoLinks);
        if (!allVideoLinks.isEmpty()) {
            //display last video in browser:
            String url = LINK_START + allVideoLinks.get(allVideoLinks.size() - 1);
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        }
    }

    public static void writeToFile(String fileName, List<String> videoLinks) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) {
            for (String videoLink : videoLinks) {
                out.write(LINK_START + videoLink);
                out.newLine();
            }
        }
    }

    public static Set<String> getVideoLinks(String fileContents) {
        Pattern p = Pattern.compile("watch\\?v=(.*?)\"");
        Matcher m = p.matcher(fileContents);
        Set<String> videoLinks = new LinkedHashSet<>(); //use Set to eliminate duplicates and LinkedHasSet to preserve order
        while (m.find()) {
            String str = m.group(1);
            if (!str.endsWith("\\")) { //saved video html page contains duplicate links that end with the character "\"
                videoLinks.add(str);
            }
        }
        System.out.println(videoLinks.size());
        return videoLinks;
    }

    public static List<String> readHTMLFiles(String dirName) throws IOException {
        File dir = new File(dirName);
        File[] files = dir.listFiles((File dir1, String name) -> name.toLowerCase().endsWith(".html"));
        List<String> htmlFileContents = new ArrayList<>();
        for (File file : files) {
            System.out.println(file.getName());
            htmlFileContents.add(readFile(file.getAbsolutePath()));
        }
        return htmlFileContents;
    }

    public static String readFile(String fileName) throws IOException {
        String fileContents;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            fileContents = sb.toString();
        }
        return fileContents;
    }

}
