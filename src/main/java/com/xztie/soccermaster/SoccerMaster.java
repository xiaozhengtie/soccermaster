package com.xztie.soccermaster;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SoccerMaster {
    private String gameResultfile;
    private Map<String, Team> teamMap;
    private int matchday;
    private StringBuffer topTeamOutput;
    private StringBuffer errorOutput;
    private static final Pattern GAME_RESULT_PATTERN = Pattern.compile("(.*) (\\d+), (.*) (\\d+)");
    private static final int NUMBER_TOP_TEAMS = 3;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please give input file");
            return;
        } else if (args.length > 1) {
            System.out.println("Please give one file name");
            return;
        }

        SoccerMaster soccerMaster = new SoccerMaster(args[0]);
        soccerMaster.validateAndProcessGameResult();
        soccerMaster.print();
    }

    public SoccerMaster(String filepath) {
        this.gameResultfile = filepath;
        this.teamMap = new HashMap<>();
        this.matchday = 1;
        this.topTeamOutput = new StringBuffer();
        this.errorOutput = new StringBuffer();
    }

    public void validateAndProcessGameResult() {
        BufferedReader br = null;
        try {
            File file = new File(gameResultfile);
            br = new BufferedReader((new FileReader(file)));
            String st;
            while ((st = br.readLine()) != null) {
                Matcher matcher = GAME_RESULT_PATTERN.matcher(st);
                if (!matcher.find()) {
                    errorOutput.append(String.format("Invalid game result: %s\n", st));
                    return;
                }

                String team1Name = matcher.group(1);
                int goals1 = Integer.parseInt(matcher.group(2));
                String team2Name = matcher.group(3);
                int goals2 = Integer.parseInt(matcher.group(4));
                int points1 = (goals1 > goals2) ? 3 : (goals1 == goals2 ? 1 : 0);
                int points2 = (points1 == 3) ? 0 : (points1 == 1 ? 1 : 3);

                if (!teamMap.containsKey(team1Name)) {
                    if (matchday > 1) {
                        errorOutput.append(String.format(
                                "Invalid game result: new team %s on matchday %d\n", team1Name, matchday));
                        return;
                    }
                    Team t = new Team(team1Name, points1, matchday);
                    teamMap.put(team1Name, t);
                } else {
                    Team t = teamMap.get(team1Name);
                    if (t.getMatchday() == matchday) {
                        if (gameResultMissingForTeam()) {
                            return;
                        }
                        computeAndStoreTopTeamsPerMatchday(true);
                        matchday++;
                    }
                    t.setPoints(t.getPoints() + points1);
                    t.setMatchday(matchday);
                }

                if (!teamMap.containsKey(team2Name)) {
                    if (matchday > 1) {
                        errorOutput.append(String.format(
                                "Invalid game result: new team %s on matchday %d\n", team2Name, matchday));
                        return;
                    }
                    Team t = new Team(team2Name, points2, matchday);
                    teamMap.put(team2Name, t);
                } else {
                    Team t = teamMap.get(team2Name);
                    t.setPoints(t.getPoints() + points2);
                    t.setMatchday(matchday);
                }
            }
        } catch (FileNotFoundException e) {
            errorOutput.append(String.format("FileNotFoundException for %s\n", gameResultfile));
            return;
        } catch (IOException e) {
            errorOutput.append(String.format("IOException: %s\n", e.toString()));
            return;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                errorOutput.append(String.format("IOException: %s\n", e.toString()));
            }
        }
        if (gameResultMissingForTeam()) {
            return;
        }
        computeAndStoreTopTeamsPerMatchday(false);
    }

    private void computeAndStoreTopTeamsPerMatchday(boolean includeTrailingNewLine) {
        topTeamOutput.append(String.format("Matchday %d\n", matchday));
        List<Team> topTeams = teamMap.values()
                .stream()
                .sorted(new Comparator<Team>() {
                    @Override
                    public int compare(Team t1, Team t2) {
                        if (t1.getPoints() != t2.getPoints()) {
                            return t2.getPoints() - t1.getPoints();
                        } else {
                            return t1.getName().compareTo(t2.getName());
                        }
                    }
                })
                .limit(NUMBER_TOP_TEAMS)
                .collect(Collectors.toList());

        for (int i = 0; i < NUMBER_TOP_TEAMS; i++) {
            String teamName = topTeams.get(i).getName();
            int points = topTeams.get(i).getPoints();
            topTeamOutput.append(
                    String.format("%s, %d %s\n", teamName, points, points > 1 ? "pts" : "pt"));
        }
        if (includeTrailingNewLine) {
            topTeamOutput.append("\n");
        }
    }

    public String getTopTeamOutput() {
        if (topTeamOutput.length() > 0) {
            return topTeamOutput.toString();
        } else {
            return null;
        }
    }

    public String getErrorOutput() {
        if (errorOutput.length() > 0) {
            return errorOutput.toString();
        } else {
            return null;
        }
    }

    private boolean gameResultMissingForTeam() {
        for (Team team : teamMap.values()) {
            if (team.getMatchday() < matchday) {
                errorOutput.append(String.format(
                        "Game result missing for team %s on matchday %d\n",
                        team.getName(), matchday
                ));
                return true;
            }
        }
        return false;
    }

    public void print() {
        if (errorOutput.length() > 0) {
            printErrors();
        } else {
            printTopTeams();
        }
    }

    private void printTopTeams() {
        System.out.print(topTeamOutput.toString());
    }

    private void printErrors() {
        System.out.print(errorOutput.toString());
    }

    class Team {
        private String name;
        private int points;
        private int matchday;

        public Team(String name, int points, int matchday) {
            this.name = name;
            this.points = points;
            this.matchday = matchday;
        }

        public String getName() {
            return this.name;
        }

        public int getMatchday() {
            return this.matchday;
        }

        public void setMatchday(int matchday) {
            this.matchday = matchday;
        }

        public int getPoints() {
            return this.points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

    }

}
