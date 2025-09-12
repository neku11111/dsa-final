package control;

import adt.ArrayQueue;
import adt.DynamicArray;
import boundary.PatientManagementUI;
import entity.Patient;

/**
 *
 * @author Phang Jun Yat
 */
public class PatientManager {

    private int nextIdNumber;
    private static DynamicArray<Patient> patientList = new DynamicArray<>();
    private PatientManagementUI ui;
    private ArrayQueue<Patient> patientQueue;

    public PatientManager() {

        ui = new PatientManagementUI();
        patientQueue = new ArrayQueue<>();

        if (patientList.isEmpty()) {
            patientList.add(new Patient("P001", "851111-01-1234", "Ling Xiao", 35, "M", "012-3456789", "12 Jalan Mawar, KL", "Hypertension"));
            patientList.add(new Patient("P002", "920222-02-2345", "MT", 30, "F", "013-9876543", "45 Jalan Melati, Penang", "Diabetes"));
            patientList.add(new Patient("P003", "990515-03-3456", "Four Fish", 26, "M", "014-2233445", "78 Jalan Anggerik, Johor", "-"));
            patientList.add(new Patient("P004", "011010-04-4567", "Tian Zi", 24, "F", "017-1122334", "32 Jalan Cempaka, Ipoh", "Asthma"));
            patientList.add(new Patient("P005", "941212-05-5678", "Tomioka Giyuu", 28, "M", "016-6677889", "90 Jalan Teratai, Malacca", "-"));
            patientList.add(new Patient("P006", "660606-06-6666", "Rengoku Kyoujurou", 45, "M", "0182345678", "99 Jalan Orchid, Seremban", "Hypertension"));
            patientList.add(new Patient("P007", "590707-07-7777", "Tokitou Muichirou", 52, "M", "0139876543", "77 Jalan Anggerik, Alor Setar", "Diabetes"));
            patientList.add(new Patient("P008", "640808-08-8888", "Kanroji Mitsuri", 60, "F", "0192233445", "88 Jalan Cempaka, Kuantan", "Arthritis"));
            patientList.add(new Patient("P009", "121212-09-9012", "Kochou Shinobu", 10, "F", "010-9988776", "88 Jalan Kenanga, Sabah", "-"));
            patientList.add(new Patient("P010", "131115-10-0123", "Uzui Tengen", 8, "M", "012-1122446", "55 Jalan Melur, Sarawak", "Diabetes"));
        }

        nextIdNumber = 1;
        for (int i = 0; i < patientList.getSize(); i++) {
            String id = patientList.getEntry(i).getId();
            int num = Integer.parseInt(id.substring(1));
            if (num >= nextIdNumber) {
                nextIdNumber = num + 1;
            }
        }
    }

    public void run() {
        int choice;
        do {
            choice = ui.Menu();
            switch (choice) {
                case 1 ->
                    registerPatient();
                case 2 ->
                    displayPatients();
                case 3 ->
                    searchPatient();
                case 4 ->
                    updatePatient();
                case 5 ->
                    deletePatient();
                case 6 ->
                    manageQueue();
                case 7 ->
                    generateReport();
                case 8 ->
                    ui.showMessage("Returning to main menu.");
                default ->
                    ui.showMessage("Invalid choice.");
            }
        } while (choice != 8);
    }

    // Register Patient Function
    private void registerPatient() {
        String newId = generatePatientId();
        Patient newPatient = ui.getPatientDetails(patientList, newId);
        patientList.add(newPatient);
        ui.showMessage("Patient registered successfull with ID: " + newId);
    }

    private String generatePatientId() {
        return String.format("P%03d", nextIdNumber++);
    }

    // Display Patient Function
    private void displayPatients() {
        if (patientList.isEmpty()) {
            ui.showMessage("No patients found.");
            return;
        }

        int choice;
        do {
            choice = ui.showDisplayMenu();
            DynamicArray<Patient> tempList = new DynamicArray<>();
            switch (choice) {
                case 1 ->
                    tempList = patientList;
                case 2 ->
                    tempList = sortByName(true);
                case 3 ->
                    tempList = sortByName(false);
                case 4 ->
                    tempList = sortByRegistrationTime(false);
                case 5 ->
                    tempList = filterByGender(ui.getGenderFilter());
                case 6 -> {
                    ui.showMessage("Returning to main menu.");
                    continue;
                }
                default -> {
                    ui.showMessage("Invalid choice.");
                    continue;
                }
            }
            ui.displayPatients(tempList);
        } while (choice != 6);
    }

    private DynamicArray<Patient> sortByName(boolean asc) {
        DynamicArray<Patient> sorted = new DynamicArray<>();
        for (int i = 0; i < patientList.getSize(); i++) {
            sorted.add(patientList.getEntry(i));
        }
        for (int i = 0; i < sorted.getSize() - 1; i++) {
            for (int j = i + 1; j < sorted.getSize(); j++) {
                Patient p1 = sorted.getEntry(i), p2 = sorted.getEntry(j);
                if ((asc && p1.getName().compareToIgnoreCase(p2.getName()) > 0)
                        || (!asc && p1.getName().compareToIgnoreCase(p2.getName()) < 0)) {
                    sorted.replace(i, p2);
                    sorted.replace(j, p1);
                }
            }
        }
        return sorted;
    }

    private DynamicArray<Patient> sortByRegistrationTime(boolean oldestFirst) {
        DynamicArray<Patient> sorted = new DynamicArray<>();
        for (int i = 0; i < patientList.getSize(); i++) {
            sorted.add(patientList.getEntry(i));
        }
        if (!oldestFirst) {
            for (int i = 0; i < sorted.getSize() / 2; i++) {
                Patient temp = sorted.getEntry(i);
                sorted.replace(i, sorted.getEntry(sorted.getSize() - 1 - i));
                sorted.replace(sorted.getSize() - 1 - i, temp);
            }
        }
        return sorted;
    }

    private DynamicArray<Patient> filterByGender(String gender) {
        DynamicArray<Patient> filter = new DynamicArray<>();
        for (int i = 0; i < patientList.getSize(); i++) {
            if (patientList.getEntry(i).getGender().equalsIgnoreCase(gender)) {
                filter.add(patientList.getEntry(i));
            }
        }
        return filter;
    }

    // Search Patient Function
    private void searchPatient() {
        if (patientList.isEmpty()) {
            ui.showMessage("No patients found.");
            return;
        }
        int choice;
        do {
            choice = ui.showSearchMenu();
            DynamicArray<Patient> result = new DynamicArray<>();
            switch (choice) {
                case 1 ->
                    result = searchByIdOrIc(ui.getSearchKeyword("Enter Patient ID or IC: "));
                case 2 ->
                    result = searchByName(ui.getSearchKeyword("Enter Name keyword: "));
                case 3 ->
                    result = searchByMedicalHistory(ui.getSearchKeyword("Enter Medical History keyword: "));
                case 4 -> {
                    ui.showMessage("Returning to main menu.");
                    continue;
                }
                default -> {
                    ui.showMessage("Invalid choice.");
                    continue;
                }
            }
            ui.displayPatients(result);
        } while (choice != 4);
    }

    private DynamicArray<Patient> searchByIdOrIc(String key) {
        DynamicArray<Patient> result = new DynamicArray<>();
        for (int i = 0; i < patientList.getSize(); i++) {
            Patient p = patientList.getEntry(i);
            if (p.getId().equalsIgnoreCase(key) || p.getIc().equalsIgnoreCase(key)) {
                result.add(p);
            }
        }
        return result;
    }

    private DynamicArray<Patient> searchByName(String keyword) {
        DynamicArray<Patient> result = new DynamicArray<>();
        for (int i = 0; i < patientList.getSize(); i++) {
            if (patientList.getEntry(i).getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(patientList.getEntry(i));
            }
        }
        return result;
    }

    private DynamicArray<Patient> searchByMedicalHistory(String keyword) {
        DynamicArray<Patient> result = new DynamicArray<>();
        for (int i = 0; i < patientList.getSize(); i++) {
            if (patientList.getEntry(i).getMedicalHistory().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(patientList.getEntry(i));
            }
        }
        return result;
    }

    //Update Patient Function
    private void updatePatient() {
        String id = ui.getInput("Enter which Patient ID to update: ");
        Patient p = findPatientById(id);
        if (p == null) {
            ui.showMessage("Patient not found.");
            return;
        }

        while (true) {
            ui.displayPatient(p);
            System.out.println("\nWhat do you want to update? (Enter 'q' to exit)");
            System.out.println("1. Name\n2. Age\n3. Gender\n4. Contact\n5. Address\n6. Medical History");
            String choiceStr = ui.getInput("Enter your choice: ");
            if (choiceStr.equalsIgnoreCase("q")) {
                ui.showMessage("Exit Update.");
                break;
            }

            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                ui.showMessage("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    String name;
                    do {
                        name = ui.getInput("Enter new Name: ");
                        if (name.isEmpty()) {
                            ui.showMessage("Name cannot be empty!");
                        }
                    } while (name.isEmpty());
                    p.setName(name);
                }
                case 2 -> {
                    int age;
                    do {
                        age = ui.getIntInput("Enter new Age: ");
                        if (age < 0) {
                            ui.showMessage("Age cannot be negative.");
                        }
                    } while (age < 0);
                    p.setAge(age);
                }
                case 3 -> {
                    String gender;
                    do {
                        gender = ui.getInput("Enter new Gender (M/F): ").toUpperCase();
                        if (gender.equals("M") || gender.equals("F")) {
                            p.setGender(gender);
                            break;
                        } else {
                            ui.showMessage("Invalid input! Must be M or F.");
                        }
                    } while (true);
                }
                case 4 -> {
                    String contact;
                    do {
                        contact = ui.getInput("Enter new Contact (10 digits, or 11 if starts with 011): ");
                        if (contact.matches("01\\d{8}") || contact.matches("011\\d{8}")) {
                            break;
                        } else {
                            ui.showMessage("Invalid contact format.");
                        }
                    } while (true);
                    p.setContact(contact);
                }
                case 5 -> {
                    String address;
                    do {
                        address = ui.getInput("Enter new Address: ");
                        if (address.isEmpty()) {
                            ui.showMessage("Address cannot be empty!");
                        }
                    } while (address.isEmpty());
                    p.setAddress(address);
                }
                case 6 -> {
                    String history = ui.getInput("Enter new Medical History (or '-' if none): ");
                    if (history.isEmpty()) {
                        history = "-";
                    }
                    p.setMedicalHistory(history);
                }
                default ->
                    ui.showMessage("Invalid choice.");
            }
            ui.showMessage("Patient updated successfull.");
        }
    }

    // Delete Patient Function
    private void deletePatient() {
        if (patientList.isEmpty()) {
            ui.showMessage("No patients in the system.");
            return;
        }
        String id = ui.getPatientId("Enter Patient ID to delete: ");
        Patient patient = findPatientById(id);
        if (patient == null) {
            ui.showMessage("Patient not found.");
            return;
        }

        if (ui.confirmDeletion(patient)) {
            int index = patientList.indexOf(patient);
            patientList.remove(index);
            ui.showMessage("Patient deleted successfull.");
        } else {
            ui.showMessage("Delete cancelled.");
        }
    }

    Patient findPatientById(String id) {
        for (int i = 0; i < patientList.getSize(); i++) {
            if (patientList.getEntry(i).getId().equalsIgnoreCase(id)) {
                return patientList.getEntry(i);
            }
        }
        return null;
    }

    private void manageQueue() {
        int choice;
        do {
            choice = ui.showQueueMenu();
            switch (choice) {
                case 1 ->
                    enqueueNormalPatient();
                case 2 ->
                    enqueuePriorityPatient();
                case 3 ->
                    serveNextPatient();
                case 4 ->
                    viewQueue();
                case 5 ->
                    removePatientFromQueue();
                case 6 ->
                    clearQueue();
                case 7 ->
                    peekNextPatient();
                case 8 ->
                    changePatientPriority();
                case 9 ->
                    ui.showMessage("Returning to main menu.");
                default ->
                    ui.showMessage("Invalid choice!");
            }
        } while (choice != 9);
    }

    private void enqueueNormalPatient() {
        String id = ui.getPatientId("Enter Patient ID to enqueue: ");
        Patient p = findPatientById(id);
        if (p == null) {
            ui.showMessage("Patient not found.");
            return;
        }

        for (int i = 0; i < patientQueue.size(); i++) {
            if (patientQueue.getEntry(i).getId().equalsIgnoreCase(p.getId())) {
                ui.showMessage("Patient is already in the queue.");
                return;
            }
        }

        p.setPriority(5);

        boolean inserted = false;
        for (int i = patientQueue.size() - 1; i >= 0; i--) {
            Patient current = patientQueue.getEntry(i);
            if (current.getPriority() == 5) {
                patientQueue.insertAt(i + 1, p);
                inserted = true;
                break;
            }
        }

        if (!inserted) {
            patientQueue.enqueue(p);
        }

        ui.showMessage("Patient added to normal queue (Priority 5).");
    }

    private void enqueuePriorityPatient() {
        String id = ui.getPatientId("Enter Patient ID to enqueue with priority: ");
        Patient p = findPatientById(id);
        if (p == null) {
            ui.showMessage("Patient not found.");
            return;
        }

        for (int i = 0; i < patientQueue.size(); i++) {
            if (patientQueue.getEntry(i).getId().equalsIgnoreCase(p.getId())) {
                ui.showMessage("Patient is already in the queue.");
                return;
            }
        }

        int priority = ui.getIntInput("Enter priority (1 = highest, 5 = lowest): ");
        if (priority < 1 || priority > 5) {
            ui.showMessage("Invalid priority!");
            return;
        }

        p.setPriority(priority);

        boolean inserted = false;
        for (int i = 0; i < patientQueue.size(); i++) {
            Patient current = patientQueue.getEntry(i);

            if (priority < current.getPriority()) {
                patientQueue.insertAt(i, p);
                inserted = true;
                break;
            }

            if (priority == current.getPriority()) {
                int j = i;
                while (j + 1 < patientQueue.size()
                        && patientQueue.getEntry(j + 1).getPriority() == priority) {
                    j++;
                }
                patientQueue.insertAt(j + 1, p);
                inserted = true;
                break;
            }
        }

        if (!inserted) {
            patientQueue.enqueue(p);
        }

        ui.showMessage("Patient added to queue with this priority " + priority);
    }

    private void serveNextPatient() {
        Patient next = patientQueue.dequeue();
        if (next == null) {
            ui.showMessage("Queue is empty!");
        } else {
            ui.showMessage("Serving patient: " + next.getName() + " (ID: " + next.getId() + ")");
        }
    }

    private void viewQueue() {
        if (patientQueue.isEmpty()) {
            ui.showMessage("Queue is empty!");
            return;
        }
        StringBuilder sb = new StringBuilder("\n--- Current Queue ---\n");
        for (int i = 0; i < patientQueue.size(); i++) {
            Patient p = patientQueue.getEntry(i);
            sb.append(i + 1).append(". ").append(p.getName()).append(" (ID: ").append(p.getId()).append(")\n");
        }
        ui.showMessage(sb.toString());
    }

    private void removePatientFromQueue() {
        String id = ui.getPatientId("Enter Patient ID to remove from queue: ");
        Patient p = findPatientById(id);
        if (p == null || !patientQueue.remove(p)) {
            ui.showMessage("Patient not found in queue.");
        } else {
            ui.showMessage("Patient removed from queue.");
        }
    }

    private void clearQueue() {
        patientQueue.clear();
        ui.showMessage("Queue cleared!");
    }

    private void peekNextPatient() {
        Patient next = patientQueue.peek();
        if (next == null) {
            ui.showMessage("Queue is empty!");
        } else {
            ui.showMessage("Next patient in queue: " + next.getName() + " (ID: " + next.getId() + ")");
        }
    }

    private void changePatientPriority() {
        String id = ui.getPatientId("Enter Patient ID to change priority: ");
        Patient p = findPatientById(id);
        if (p == null) {
            ui.showMessage("Patient not found.");
            return;
        }

        int newPriority = ui.getIntInput("Enter new priority (1 = highest, 5 = lowest): ");
        if (newPriority < 1 || newPriority > 5) {
            ui.showMessage("Invalid priority!");
            return;
        }

        if (!patientQueue.remove(p)) {
            ui.showMessage("Patient not found in queue!");
            return;
        }

        p.setPriority(newPriority);

        boolean inserted = false;
        for (int i = 0; i < patientQueue.size(); i++) {
            Patient current = patientQueue.getEntry(i);

            if (newPriority < current.getPriority()) {
                patientQueue.insertAt(i, p);
                inserted = true;
                break;
            }

            if (newPriority == current.getPriority()) {
                int j = i;
                while (j + 1 < patientQueue.size()
                        && patientQueue.getEntry(j + 1).getPriority() == newPriority) {
                    j++;
                }
                patientQueue.insertAt(j + 1, p);
                inserted = true;
                break;
            }
        }

        if (!inserted) {
            patientQueue.enqueue(p);
        }

        ui.showMessage("Priority updated for patient " + p.getName() + " (ID: " + p.getId() + ")");
    }

    // Report FUnction
    private void generateReport() {
        int choice;
        do {
            choice = ui.showReportMenu();
            switch (choice) {
                case 1 ->
                    generateMedicalHistoryReport();
                case 2 ->
                    generateGenderReport();
                case 3 ->
                    generateAgeGroupReport();
                case 4 ->
                    ui.showMessage("Returning to main menu...");
                default ->
                    ui.showMessage("Invalid choice!");
            }
        } while (choice != 4);
    }

    private static final String GREEN_BG = "\u001B[42m";
    private static final String BLUE_BG = "\u001B[44m";
    private static final String RED_BG = "\u001B[41m";
    private static final String RESET = "\u001B[0m";

    private void generateMedicalHistoryReport() {
        ui.showMessage("\n--- Medical History Report ---");
        ui.showMessage("==========================================");

        DynamicArray<String> uniqueHistories = new DynamicArray<>();
        DynamicArray<Integer> counts = new DynamicArray<>();

        for (int i = 0; i < patientList.getSize(); i++) {
            String history = patientList.getEntry(i).getMedicalHistory().trim();
            if (history.isEmpty()) {
                history = "-";
            }

            int index = -1;
            for (int j = 0; j < uniqueHistories.getSize(); j++) {
                if (uniqueHistories.getEntry(j).equalsIgnoreCase(history)) {
                    index = j;
                    break;
                }
            }

            if (index == -1) {

                uniqueHistories.add(history);
                counts.add(1);
            } else {

                counts.replace(index, counts.getEntry(index) + 1);
            }
        }

        int total = patientList.getSize();
        ui.showMessage("Total Patients: " + total + "\n");

        ui.showMessage("Medical History Distribution:");
        int maxBarLength = 30;

        for (int i = 0; i < uniqueHistories.getSize(); i++) {
            String history = uniqueHistories.getEntry(i);
            int count = counts.getEntry(i);

            double percent = (double) count / total;
            int barLength = (int) Math.ceil(percent * maxBarLength);

            String color = history.equals("-") ? RED_BG : BLUE_BG;

            ui.showMessage(String.format("%-15s | %s %d (%.1f%%)",
                    history,
                    createColoredBar(barLength, color),
                    count,
                    percent * 100));
        }
    }

    private void generateGenderReport() {
        ui.showMessage("\n--- Gender Distribution Report ---");
        ui.showMessage("==========================================");

        int male = 0, female = 0;
        for (int i = 0; i < patientList.getSize(); i++) {
            if (patientList.getEntry(i).getGender().equalsIgnoreCase("M")) {
                male++;
            } else {
                female++;
            }
        }

        ui.showMessage("Total Patients: " + patientList.getSize());
        ui.showMessage("Male: " + male);
        ui.showMessage("Female: " + female);
        ui.showMessage("");

        int maxBarLength = 30;
        double malePercent = (double) male / patientList.getSize();
        double femalePercent = (double) female / patientList.getSize();

        int maleBars = (int) Math.ceil(malePercent * maxBarLength);
        int femaleBars = (int) Math.ceil(femalePercent * maxBarLength);

        ui.showMessage("Gender Distribution:");
        ui.showMessage("Male:   " + createColoredBar(maleBars, BLUE_BG) + " "
                + String.format("%.1f%%", malePercent * 100));
        ui.showMessage("Female: " + createColoredBar(femaleBars, RED_BG) + " "
                + String.format("%.1f%%", femalePercent * 100));
    }

    private void generateAgeGroupReport() {
        ui.showMessage("\n--- Age Group Distribution Report ---");
        ui.showMessage("==========================================");

        int child = 0, youngAdult = 0, adult = 0, senior = 0;

        for (int i = 0; i < patientList.getSize(); i++) {
            int age = patientList.getEntry(i).getAge();
            if (age <= 18) {
                child++;
            } else if (age <= 40) {
                youngAdult++;
            } else if (age <= 60) {
                adult++;
            } else {
                senior++;
            }
        }

        ui.showMessage("Total Patients: " + patientList.getSize());
        ui.showMessage("Children (0-18): " + child);
        ui.showMessage("Young Adults (19-40): " + youngAdult);
        ui.showMessage("Adults (41-60): " + adult);
        ui.showMessage("Seniors (60+): " + senior);
        ui.showMessage("");

        int maxBarLength = 30;
        double childPercent = (double) child / patientList.getSize();
        double youngAdultPercent = (double) youngAdult / patientList.getSize();
        double adultPercent = (double) adult / patientList.getSize();
        double seniorPercent = (double) senior / patientList.getSize();

        int childBars = (int) Math.ceil(childPercent * maxBarLength);
        int youngAdultBars = (int) Math.ceil(youngAdultPercent * maxBarLength);
        int adultBars = (int) Math.ceil(adultPercent * maxBarLength);
        int seniorBars = (int) Math.ceil(seniorPercent * maxBarLength);

        ui.showMessage("Age Group Distribution:");
        ui.showMessage("0-18:    " + createColoredBar(childBars, RED_BG) + " "
                + String.format("%.1f%%", childPercent * 100));
        ui.showMessage("19-40:   " + createColoredBar(youngAdultBars, GREEN_BG) + " "
                + String.format("%.1f%%", youngAdultPercent * 100));
        ui.showMessage("41-60:   " + createColoredBar(adultBars, RED_BG) + " "
                + String.format("%.1f%%", adultPercent * 100));
        ui.showMessage("60+:     " + createColoredBar(seniorBars, GREEN_BG) + " "
                + String.format("%.1f%%", seniorPercent * 100));
    }

// Helper method to create colored bar strings
    private String createColoredBar(int length, String colorCode) {
        if (length == 0) {
            return "";
        }

        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < length; i++) {
            bar.append(colorCode).append(" ").append(RESET);
        }
        return bar.toString();
    }

}
