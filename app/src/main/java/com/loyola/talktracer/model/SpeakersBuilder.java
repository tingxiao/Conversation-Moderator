package com.loyola.talktracer.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * maintains the Speakers, the times, the colors to draw the bars
 */
public class SpeakersBuilder {
    private static final String TAG = "SpeakersBuilder";
    private static final int[] speakerColors = {
            0xb0ff6600,
            0xb0ffE600,
            0xb099ff00,
            0xb01aff00,
            0xb0ff001a,
            0xb0ff8b3d,
            0xb0ffaf7a,
            0xb000ff66,
            0xb0ff0099,
            0xb07acaff,
            0xb03db1ff,
            0xb000ffe6,
            0xb0e600ff,
            0xb06600ff,
            0xb0001aff,
            0xb00099ff};
    private HashMap<String, Speaker> speakerMap = new HashMap<String, Speaker>();

    public SpeakersBuilder() {
        Log.i(TAG, "SpeakersBuilder()");
    }

    public void logSegStream(InputStream in) throws IOException {
        Log.i(TAG, "parseSegStream()");
        Reader r = new BufferedReader(new InputStreamReader(in));
        StreamTokenizer st = new StreamTokenizer(r);
        while (st.nextToken() != StreamTokenizer.TT_EOF) { // show name
            String showName=st.sval;
            st.nextToken();
            String chanelNumber=st.sval;
            st.nextToken();
            String startTimeinMili=Double.toString(st.nval);
            st.nextToken();
            String durationMili=Double.toString(st.nval);
            st.nextToken();
            String gender=st.sval;
            st.nextToken();
            String band=st.sval;
            st.nextToken();
            String envtype= st.sval;
            st.nextToken();
            String idk=st.sval;
            Log.d("hey", "dis da " +showName +" " +chanelNumber+" "+ startTimeinMili+" "+ durationMili+ " "+ gender+" "+ " "+band+" "+envtype+ " "+idk);


        }
    }

    public SpeakersBuilder parseSegStream(InputStream in) throws IOException {
        Log.i(TAG, "parseSegStream()");
      /*  Reader r = new BufferedReader(new InputStreamReader(in));
        StreamTokenizer st = new StreamTokenizer(r);
        while (st.nextToken() != StreamTokenizer.TT_EOF) { // show name
            st.nextToken(); // the channel number
            st.nextToken(); // the start of the segment (in features)
            // convert centiseconds to milliseconds
            long startTimeInMilliseconds = (long) st.nval * 10;
            st.nextToken(); // the length of the segment (in features)
            // convert centiseconds to milliseconds
            long durationInMilliseconds = (long) st.nval * 10;
            st.nextToken(); // the speaker gender (U=unknown, F=female, M=Male)
            char gender = st.sval.charAt(0);
            Log.d("hey", "gender is " +gender);
            st.nextToken(); // the type of band (T=telephone, S=studio)
            st.nextToken(); // the type of environment (music, speech only, …)
            st.nextToken(); // the speaker label
            String name = st.sval;
            add(startTimeInMilliseconds, durationInMilliseconds, name, gender);
        }*/






        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        String line;
        String show = null;
        String name = null;

        while ((line = r.readLine()) != null) {
            char[] linechar = line.toCharArray();
            System.out.println("**************************************** "+linechar.toString());
            if (linechar.length == 0) {
                continue; // empty line
            }
            System.out.println("**************************************** "+linechar);
            if (linechar[0] == '\n') {
                continue; // empty line
            }
            if (linechar[0] == '#') {
                continue; // empty line
            }
            if ((linechar[0] == ';') && (linechar[1] == ';')) {
                continue; // rem line
            }
            String segmentChannel = "1";
            int segmentStart = 0;
            int segmentLen = 0;
            String segmentGender = null;
            String segmentBand = null;
            String segmentEnvironement = null;
            StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
            int result = 0;
            while (stringTokenizer.hasMoreTokens()) {

                if (result == 0) {
                    show = stringTokenizer.nextToken();
                } else if (result == 1) {
                    segmentChannel = stringTokenizer.nextToken();
                } else if (result == 2) {
                    segmentStart = Integer.parseInt(stringTokenizer.nextToken());
                } else if (result == 3) {
                    segmentLen = Integer.parseInt(stringTokenizer.nextToken());
                } else if (result == 4) {
                    segmentGender = stringTokenizer.nextToken();
                } else if (result == 5) {
                    segmentBand = stringTokenizer.nextToken();
                } else if (result == 6) {
                    segmentEnvironement = stringTokenizer.nextToken();
                } else if (result == 7) {
                    name = stringTokenizer.nextToken();
                    break;
                }
                result++;
            }
            long startTimeInMilliseconds = (long) segmentStart * 10;
            long durationInMilliseconds = (long) segmentLen * 10;
            System.out.println("SpeadkersBuilder - parseSegStream - the values sent for storing are "+startTimeInMilliseconds+" - "+durationInMilliseconds+" - "+name+" - "+segmentGender.charAt(0));
            add(startTimeInMilliseconds, durationInMilliseconds, name, segmentGender.charAt(0));
            if (result != 7) {
                throw new IOException("segmentation read error \n" + line + "\n ");
            }

        }






        return this;
    }

    public SpeakersBuilder add(long startTimeInMilliseconds, long durationInMilliseconds, String name, char gender) {
        Log.i("BULO", "add() start: " + startTimeInMilliseconds + " duration: " + durationInMilliseconds +
                " name: " + name + " gender: " + gender);
        Speaker speaker = speakerMap.get(name);
        if (speaker == null) {
            speaker = new Speaker(name, gender);
            speakerMap.put(name, speaker);
        }
        speaker.addTurn(startTimeInMilliseconds, durationInMilliseconds);
        return this;
    }

    public ArrayList<Speaker> build() {
        Log.i(TAG, "build()");
        ArrayList<Speaker> speakers = new ArrayList<>(speakerMap.values());
//        Collections.sort(speakers, Collections.reverseOrder());
        int speakerNum = 1;
        for (Speaker speaker : speakers) {
            // TODO: Use the voice recognition API and analyze the speaker's names
            speaker.setName("S" + speakerNum++);
        }
        return colorize(speakers);
    }

    private ArrayList<Speaker> colorize(ArrayList<Speaker> speakers) {
        Log.i(TAG, "colorize()");
        for (int i = 0; i < speakers.size(); i++) {
            speakers.get(i).setColor(speakerColors[i % speakerColors.length]);
        }
        return speakers;
    }
}
