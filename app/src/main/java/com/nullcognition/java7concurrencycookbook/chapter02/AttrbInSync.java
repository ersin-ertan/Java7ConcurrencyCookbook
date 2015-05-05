package com.nullcognition.java7concurrencycookbook.chapter02;// Created by ersin on 04/05/15

import java.util.Random;

public class AttrbInSync implements Runnable {

    private Cinema cinema;
    Random r = new Random(1);

    public AttrbInSync(Cinema c) {
        cinema = c;
    }

    @Override
    public void run() {
        cinema.sellTickets1(getRand());
        cinema.sellTickets1(getRand());
        cinema.sellTickets2(getRand());
        cinema.sellTickets2(getRand());
        cinema.returnTickets1(getRand());
        cinema.returnTickets2(getRand());
        cinema.returnTickets2(getRand());
        cinema.sellTickets1(getRand());
        cinema.sellTickets1(getRand());
        cinema.sellTickets2(getRand());
    }

    public int getRand() {
        return r.nextInt((20 - 1) + 1) + 1;
    }

    public static class Cinema {
        private long vacantC1, vacantC2;
        private final Object controlC1, controlC2;

        public Cinema() {
            controlC1 = new Object();
            controlC2 = new Object();
            vacantC1 = 20;
            vacantC2 = 20;
        }

        public boolean sellTickets1(int number) {
            synchronized (controlC1) {
                if (number < vacantC1) {
                    vacantC1 -= number;
                    return true;
                }
                return false;
            }
        }

        public boolean sellTickets2(int number) {
            synchronized (controlC2) {
                if (number < vacantC2) {
                    vacantC2 -= number;
                    return true;
                }
                return false;
            }
        }

        public boolean returnTickets1(int number) {
            synchronized (controlC1) {
                if (number + vacantC1 < 21) {
                    vacantC1 += number;
                    return true;
                }
                return false;
            }
        }

        public boolean returnTickets2(int number) {
            synchronized (controlC2) {
                if (number + vacantC2 < 21) {
                    vacantC2 += number;
                    return true;
                }
                return false;
            }
        }

        public long getVacantC1() {
            return vacantC1;
        }

        public long getVacantC2() {
            return vacantC2;
        }
    }

}
