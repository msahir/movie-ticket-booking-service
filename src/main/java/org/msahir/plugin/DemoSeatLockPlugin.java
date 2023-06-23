package org.msahir.plugin;

import com.google.common.collect.ImmutableList;
import org.msahir.exceptions.SeatTemporaryUnavailableException;
import org.msahir.model.Seat;
import org.msahir.model.SeatLock;
import org.msahir.model.Show;
import lombok.NonNull;

import java.util.*;

public class DemoSeatLockPlugin implements SeatLockPlugin {

    private final Integer lockTimeout;
    private final Map<Show, Map<Seat, SeatLock>> locks;

    public DemoSeatLockPlugin(@NonNull final Integer lockTimeout) {
        this.locks = new HashMap<>();
        this.lockTimeout = lockTimeout;
    }

    @Override
    synchronized public void lockSeats(@NonNull final Show show, @NonNull final List<Seat> seats,
                                       @NonNull final String user) {

        seats.forEach(seat -> {
            if (isSeatLocked(show, seat)) {
                throw new SeatTemporaryUnavailableException();
            }
        });

        seats.forEach(seat -> {
            lockSeat(show, seat, user, lockTimeout);
        });

    }

    @Override
    public void unlockSeats(@NonNull final Show show, @NonNull final List<Seat> seats, @NonNull final String user) {
        seats.forEach(seat -> {
            if (validateLock(show, seat, user)) {
                unlockSeat(show, seat);
            }
        });
    }

    @Override
    public boolean validateLock(@NonNull final Show show, @NonNull final Seat seat, @NonNull final String user) {
        return isSeatLocked(show, seat)
                && locks.get(show).get(seat).getLockedBy().equals(user);
    }

    @Override
    public List<Seat> getLockedSeats(@NonNull final Show show) {
        if (!locks.containsKey(show)) {
            return ImmutableList.of();
        }
        final List<Seat> lockedSeats = new ArrayList<>();

        locks.get(show).keySet().forEach(seat -> {
                if (isSeatLocked(show, seat)) {
                    lockedSeats.add(seat);
                }
            });
        return lockedSeats;
    }

    private void unlockSeat(final Show show, final Seat seat) {
        if (!locks.containsKey(show)) {
            return;
        }
        locks.get(show).remove(seat);
    }

    private void lockSeat(final Show show, final Seat seat, final String user, final Integer timeoutInSeconds) {
        if (!locks.containsKey(show)) {
            locks.put(show, new HashMap<>());
        }
        final SeatLock lock = new SeatLock(seat, show, timeoutInSeconds, new Date(), user);
        locks.get(show).put(seat, lock);
    }

    private boolean isSeatLocked(final Show show, final Seat seat) {
        return locks.containsKey(show)
                && locks.get(show).containsKey(seat)
                && !locks.get(show).get(seat).isLockExpired();
    }
}
