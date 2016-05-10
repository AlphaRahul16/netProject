/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.report;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qait.automation.utils.DateUtil;

/**
 *
 * @author QAIT
 */
public class ReformatTestFile {

    String replacealltimestamp(String html) {

        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile("[0-9]{13}")
                .matcher(html);

        while (m.find()) {
            allMatches.add(m.group());
        }
        for (String entrySet : allMatches) {
            for (int i = 1; i <= 10; i++) {
                html = html.replace("<td rowspan=\"" + i + "\">" + entrySet + "</td>",
                        "<td rowspan=\"" + i + "\">" + DateUtil.converttimestamp(entrySet) + "</td>");
            }
        }
        return html;
    }

    void writeLargerTextFile(String aFileName, String html) throws IOException {
        Path path = Paths.get(aFileName);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(aFileName), "ISO-8859-1"))) {
            writer.write(html);
        }
    }

    String readLargerTextFile(String aFileName) throws IOException {
        String html = "";
        Path path = Paths.get(aFileName);
        try (Scanner scanner = new Scanner(path, "ISO-8859-1")) {
            while (scanner.hasNextLine()) {
                //process each line in some way
                html = html + scanner.nextLine() + "\n";
            }
        }
        return html;
    }
}
