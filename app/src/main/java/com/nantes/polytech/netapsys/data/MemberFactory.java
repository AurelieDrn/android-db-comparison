package com.nantes.polytech.netapsys.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Aurelie on 19/03/2017.
 */

public class MemberFactory {

    public static ArrayList<MemberData> getMemberData(int nbMembers) {
        List<String> names = new ArrayList<>(Arrays.asList("Leclair", "Jalbert", "Lampron", "Duranseau", "CinqMars", "Lazure", "Allain", "Raymond", "Bossé", "Boulanger"));
        List<String> firstNames = new ArrayList<>(Arrays.asList("Bruno", "Emmeline", "Alexandrie", "Thibaut", "Matthieu", "Clothilde", "Agathe", "Moore", "Gill", "Alain"));
        List<Integer> ages = new ArrayList<>(Arrays.asList(34, 61, 70, 32, 36, 75, 41, 82, 33));
        List<String> addresses = new ArrayList<>(Arrays.asList("81 Cours Franklin Roosevelt", "27 Rue Marie De Médicis", "75 Rue Marie De Médicis", "18 Chemin des Bateliers", "25 Rue Petite Fusterie", "22 Cours Marechal-Joffre", "98 Boulevard Amiral Courbet", "25 Rue des Lacs", "43 Rue Descartes", "39 Rue Joseph Vernet"));
        List<String> phoneNumbers = new ArrayList<>(Arrays.asList("04.09.32.28.72", "04.27.93.13.87", "04.51.78.39.73", "04.42.38.76.22", "04.09.28.70.32", "05.80.39.55.78", "01.48.36.57.28", "03.87.08.66.29", "01.02.92.74.91", "04.35.21.01.61"));

        List<MemberData> listMember = new ArrayList<MemberData>();

        for(int i=0; i<nbMembers; i++) {
            String name = names.get((int)(Math.random() * names.size()));
            String firstName = firstNames.get((int)(Math.random() * firstNames.size()));
            Integer age = ages.get((int)(Math.random() * ages.size()));
            String address = addresses.get((int)(Math.random() * addresses.size()));
            String tel = phoneNumbers.get((int)(Math.random() * phoneNumbers.size()));

            MemberData m = new MemberData(name, firstName, age, address, tel);
            listMember.add(m);
        }

        return (ArrayList<MemberData>) listMember;
    }
}
