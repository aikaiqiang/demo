package kaywall.top.example.openfile;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class KeywordScanner {
    public static void main(String[] args) {
        String directoryPath = "C:\\Users\\Administrator\\Desktop\\download-20250620101944";
        List<String> keyList = new ArrayList<>();
        keyList.add("FY2024");
        keyList.add("M_M13");
        keyList.add("HB35");
//        keyList.add("R1103_Input");
        String[] keywords = keyList.toArray(new String[0]);

        Path dirPath = Paths.get(directoryPath);
        if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
            System.err.println("无效的目录路径！");
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("C:\\Users\\Administrator\\Desktop\\result2.txt"))) {
            Files.walk(dirPath)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> searchFileForKeywords(path, keywords, writer));
        } catch (IOException e) {
            System.err.println("处理文件时发生错误: " + e.getMessage());
        }
    }

    private static void searchFileForKeywords(Path filePath, String[] keywords, BufferedWriter writer) {
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                boolean allKeywordsFound = Arrays.stream(keywords).allMatch(line::contains);

                if (allKeywordsFound) {
                    System.out.println("文件: " + filePath.getFileName() + "\n");
                    writer.write("文件: " + filePath.getFileName() + "\n");
                    System.out.println("行号: " + lineNumber + "\n");
                    writer.write("行号: " + lineNumber + "\n");
                    System.out.println("匹配的行: " + line.trim() + "\n");
                    writer.write("匹配的行: " + line.trim() + "\n");
                    System.out.println("-".repeat(40) + "\n");
                    writer.write("-".repeat(40) + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("读取文件 " + filePath.getFileName() + " 时发生错误: " + e.getMessage());
        }
    }
}
