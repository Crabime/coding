package converter;

import java.util.Date;

/**
 * Created by crabime on 9/15/17.
 */
public class Reservation {
    private Date time;
    private Player player;
    private SportType type;
    private int duration;

    public Reservation() {
    }

    public Reservation(Date time, Player player, int duration, SportType type) {
        this.time = time;
        this.player = player;
        this.duration = duration;
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public SportType getType() {
        return type;
    }

    public void setType(SportType type) {
        this.type = type;
    }
}
