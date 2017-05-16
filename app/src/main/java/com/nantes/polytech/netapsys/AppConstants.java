package com.nantes.polytech.netapsys;

import com.nantes.polytech.netapsys.data.MemberData;
import com.nantes.polytech.netapsys.data.MemberFactory;

import java.util.List;

/**
 * Created by Aurelie on 22/12/2016.
 */

public final class AppConstants {

    private AppConstants() {
    }

    public static final int EXEC_TIME = 100;
    public static final int NB_NUPLETS = 100;
    public static final List<MemberData> listMember = MemberFactory.getMemberData(NB_NUPLETS);
}
