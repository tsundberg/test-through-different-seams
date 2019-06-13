package se.thinkcode.steps.adapters;

public class AdapterFactory {

    public TodoAdapter getAdapter() {
        String seam = getSeam();

        if (seam.equals("model")) {
            return new ModelAdapter();
        }

        if (seam.equals("rest")) {
            return new RestAdapter();
        }

        System.err.println("No seam, database, browser, device, or client specified. Will default to model in memory");
        return new ModelAdapter();
    }

    private String getSeam() {
        if (System.getProperty("seam") != null) {
            return System.getProperty("seam");
        }

        System.err.println("No seam specified. Specify with -Dseam=<your seam name> Will default to model");
        return "model";
    }
}