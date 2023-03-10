/*
 * @File:   NBody.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/6 下午7:25
 * @Version:0.0
 */


public class NBody {
    public static double readRadius(String str) {
        In in = new In(str);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String str) {
        In in = new In(str);
        int numberOfPlanet = in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[numberOfPlanet];
        for ( int i = 0 ; i < numberOfPlanet ; i++ ) {
            double pX = in.readDouble();
            double pY = in.readDouble();
            double vX = in.readDouble();
            double vY = in.readDouble();
            double mass = in.readDouble();
            String imgFile = in.readString();
            planets[i] = new Planet(pX, pY, vX, vY, mass, imgFile);
        }
        return planets;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            return ;
        }
        // collect information
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String fileName = args[2];

        // get information
        double radius = readRadius(fileName);
        Planet[] planets = readPlanets(fileName);

        // set background image
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");

        // Draw All the Planets
        for (Planet planet : planets) {
            StdDraw.picture(planet.xxPos, planet.yyPos, String.format("%s%s", "images/", planet.imgFileName));
        }
        // enable double buffering
        StdDraw.enableDoubleBuffering();

        for ( double t = 0; t < T; t += dt ) {
            // create force array
            double[] xForceArr = new double[planets.length];
            double[] yForceArr = new double[planets.length];

            // calculate the net force of each planet
            for ( int i = 0; i < planets.length; i++ ) {
                xForceArr[i] = planets[i].calcNetForceExertedByX(planets);
                yForceArr[i] = planets[i].calcNetForceExertedByY(planets);
            }

            // update the location of planets
            for ( int i = 0; i < planets.length; i++ ) {
                planets[i].update(dt, xForceArr[i], yForceArr[i]);
            }

            // draw background image
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet planet: planets) {
//                StdDraw.picture(planet.xxPos, planet.yyPos, String.format("%s%s", "images/", planet.imgFileName));
                planet.draw();
            }

            // show offscreen buffer
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for ( int i = 0; i < planets.length; i++ ) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos,
                    planets[i].yyPos,
                    planets[i].xxVel,
                    planets[i].yyVel,
                    planets[i].mass,
                    planets[i].imgFileName
            );
        }
    }
}