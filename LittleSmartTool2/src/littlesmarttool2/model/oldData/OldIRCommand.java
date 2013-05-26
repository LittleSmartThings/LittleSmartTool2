/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model.oldData;

import com.fasterxml.jackson.annotation.*;

/**
 *
 * @author marcher89
 */
class OldIRCommand {
    public final String id;
    public final String action;
    public final int[] highdurations;
    public final int[] lowdurations;
    public final int highfreqperiods;
    public final int lowfreqperiods;
    public final int signalrepeat;
    
    @JsonCreator
        public OldIRCommand(@JsonProperty("id") String id, 
        @JsonProperty("action") String action, 
        @JsonProperty("high-durations") int[] highdurations, 
        @JsonProperty("low-durations") int[] lowdurations, 
        @JsonProperty("high-freq-periods") int highfreqperiods, 
        @JsonProperty("low-freq-periods") int lowfreqperiods, 
        @JsonProperty("signal-repeat") int signalrepeat){
            this.id=id;
            this.action=action;
            this.highdurations=highdurations;
            this.lowdurations=lowdurations;
            this.highfreqperiods=highfreqperiods;
            this.lowfreqperiods=lowfreqperiods;
            this.signalrepeat=signalrepeat;
        }
}
