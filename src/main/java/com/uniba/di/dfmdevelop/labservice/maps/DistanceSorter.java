package com.uniba.di.dfmdevelop.labservice.maps;

import java.util.Comparator;

public class DistanceSorter implements Comparator<LaboratorioDistanza> {

    @Override
    public int compare(LaboratorioDistanza o1, LaboratorioDistanza o2) {
        return o2.getDistanza().compareTo(o1.getDistanza());
    }
}
