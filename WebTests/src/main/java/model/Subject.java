package model;

/**
 * Created by Elizaveta on 21.05.2016.
 */
public enum Subject { ALGEBRA , GEOGRAPHY, ENGLISH, COMPUTER_SCIENCE, RUSSIAN, LITERATURE, GEOMETRY, OTHER;

    @Override
    public String toString() {
        switch (this){
            case ALGEBRA: return "ALGEBRA";
            case GEOGRAPHY: return "GEOGRAPHY";
            case ENGLISH: return "ENGLISH";
            case COMPUTER_SCIENCE: return "COMPUTER_SCIENCE";
            case RUSSIAN: return "RUSSIAN";
            case LITERATURE: return "LITERATURE";
            case GEOMETRY: return "GEOMETRY";
            default: return "OTHER";
        }
    }
}