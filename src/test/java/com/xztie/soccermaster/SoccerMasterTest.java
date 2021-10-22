package com.xztie.soccermaster;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class SoccerMasterTest {
    private String expectedOutput(String path) {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {
            File file = new File(path);
            br = new BufferedReader((new FileReader(file)));
            String st;
            while ((st = br.readLine()) != null) {
                sb.append(st);
                sb.append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.format("FileNotFoundException for %s\n", "src/test/resources/expected-output1.txt");
        } catch (IOException e) {
            System.out.format("IOException: %s", e.toString());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                System.out.format("IOException: %s", e.toString());
            }
        }
        return sb.toString();
    }

    @Test
    public void validInputTest() {
        SoccerMaster soccerMaster = new SoccerMaster(
                "src/test/resources/sample-input1.txt");
        soccerMaster.validateAndProcessGameResult();

        Assert.assertEquals(
                expectedOutput("src/test/resources/expected-output1.txt"),
                soccerMaster.getTopTeamOutput());
    }

    @Test
    public void FileNotFoundTest() {
        SoccerMaster soccerMaster = new SoccerMaster(
                "src/test/resources/nonexistent-input.txt");
        soccerMaster.validateAndProcessGameResult();

        Assert.assertEquals(
                "FileNotFoundException for src/test/resources/nonexistent-input.txt\n",
                soccerMaster.getErrorOutput());
    }

    @Test
    public void invalidGameResultTest() {
        SoccerMaster soccerMaster = new SoccerMaster(
                "src/test/resources/sample-input2.txt");
        soccerMaster.validateAndProcessGameResult();

        Assert.assertEquals(
                expectedOutput("src/test/resources/expected-output2.txt"),
                soccerMaster.getErrorOutput());
    }

    @Test
    public void invalidGameResultNewTeamTest() {
        SoccerMaster soccerMaster = new SoccerMaster(
                "src/test/resources/sample-input3.txt");
        soccerMaster.validateAndProcessGameResult();

        Assert.assertEquals(
                expectedOutput("src/test/resources/expected-output3.txt"),
                soccerMaster.getErrorOutput());
    }

    @Test
    public void gameResultMissingForTeamTest() {
        SoccerMaster soccerMaster = new SoccerMaster(
                "src/test/resources/sample-input4.txt");
        soccerMaster.validateAndProcessGameResult();

        Assert.assertEquals(
                expectedOutput("src/test/resources/expected-output4.txt"),
                soccerMaster.getErrorOutput());
    }

}
