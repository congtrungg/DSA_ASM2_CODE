import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Student {
    private String studentID;
    private String fullName;
    private double mark;

    public Student(String studentID, String fullName, double mark) {
        this.studentID = studentID;
        this.fullName = fullName;
        this.mark = mark;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getRanking() {
        if (mark >= 0 && mark < 5.0) {
            return "Failed";
        } else if (mark >= 5.0 && mark < 6.5) {
            return "Medium";
        } else if (mark >= 6.5 && mark < 7.5) {
            return "Good";
        } else if (mark >= 7.5 && mark < 9.0) {
            return "Very Good";
        } else if (mark >= 9.0 && mark <= 10.0) {
            return "Excellent";
        } else {
            return "Invalid mark";
        }
    }

    @Override
    public String toString() {
        return "StudentID: " + studentID + ", Full Name: " + fullName + ", Mark: " + mark + ", Ranking: " + getRanking();
    }
}

class TreeNode {
    Student student;
    TreeNode left;
    TreeNode right;

    TreeNode(Student student) {
        this.student = student;
        left = null;
        right = null;
    }
}

class StudentBST {
    private TreeNode root;

    public void insert(Student student) {
        root = insertRec(root, student);
    }

    private TreeNode insertRec(TreeNode root, Student student) {
        if (root == null) {
            root = new TreeNode(student);
            return root;
        }

        if (student.getStudentID().compareTo(root.student.getStudentID()) < 0) {
            root.left = insertRec(root.left, student);
        } else if (student.getStudentID().compareTo(root.student.getStudentID()) > 0) {
            root.right = insertRec(root.right, student);
        }

        return root;
    }

    public void delete(String studentID) {
        root = deleteRec(root, studentID);
    }

    private TreeNode deleteRec(TreeNode root, String studentID) {
        if (root == null) {
            return root;
        }

        if (studentID.compareTo(root.student.getStudentID()) < 0) {
            root.left = deleteRec(root.left, studentID);
        } else if (studentID.compareTo(root.student.getStudentID()) > 0) {
            root.right = deleteRec(root.right, studentID);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            root.student = findMin(root.right).student;
            root.right = deleteRec(root.right, root.student.getStudentID());
        }

        return root;
    }

    private TreeNode findMin(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    public Student search(String studentID) {
        return searchRec(root, studentID);
    }

    private Student searchRec(TreeNode root, String studentID) {
        if (root == null || root.student.getStudentID().equals(studentID)) {
            return (root != null) ? root.student : null;
        }

        if (studentID.compareTo(root.student.getStudentID()) < 0) {
            return searchRec(root.left, studentID);
        }

        return searchRec(root.right, studentID);
    }

    public ArrayList<Student> inorder() {
        ArrayList<Student> students = new ArrayList<>();
        inorderRec(root, students);
        return students;
    }

    private void inorderRec(TreeNode root, ArrayList<Student> students) {
        if (root != null) {
            inorderRec(root.left, students);
            students.add(root.student);
            inorderRec(root.right, students);
        }
    }
}

class StudentManager {
    private StudentBST studentBST = new StudentBST();
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent() {
        try {
            System.out.print("Enter Student ID: ");
            String studentID = scanner.nextLine();
            System.out.print("Enter Full Name: ");
            String fullName = scanner.nextLine();
            System.out.print("Enter Mark: ");
            double mark = Double.parseDouble(scanner.nextLine()); // Handle NumberFormatException
            students.add(new Student(studentID, fullName, mark));
            studentBST.insert(new Student(studentID, fullName, mark));
            System.out.println("Student added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid mark entered. Please enter a numeric value.");
        }
    }


    public void editStudent() {
        System.out.print("Enter Student ID or Full Name to edit: ");
        String searchQuery = scanner.nextLine().toLowerCase();
        Student student = studentBST.search(searchQuery);
        if (student != null) {
            System.out.print("Enter new Full Name: ");
            student.setFullName(scanner.nextLine());
            System.out.print("Enter new Mark: ");
            try {
                student.setMark(Double.parseDouble(scanner.nextLine())); // Handle NumberFormatException
                System.out.println("Student updated successfully.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid mark entered. Please enter a numeric value.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    public void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        String studentID = scanner.nextLine();
        if (studentBST.search(studentID) != null) {
            studentBST.delete(studentID);
            students.removeIf(student -> student.getStudentID().equals(studentID));
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }


    public void searchStudent() {
        System.out.print("Enter Student ID to search: ");
        String studentID = scanner.nextLine();
        Student student = studentBST.search(studentID);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    public void displayStudents() {
        ArrayList<Student> students = studentBST.inorder();
        if (students.isEmpty()) {
            System.out.println("No students to display.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    public void sortStudents() {
        System.out.println("Choose sorting method:");
        System.out.println("1. Quick Sort");
        System.out.println("2. Selection Sort");
        System.out.println("3. Merge Sort");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                quickSortStudents(0, students.size() - 1);
                System.out.println("Students sorted by Quick Sort.");
                break;
            case 2:
                selectionSortStudents();
                System.out.println("Students sorted by Selection Sort.");
                break;
            case 3:
                mergeSortStudents(0, students.size() - 1);
                System.out.println("Students sorted by Merge Sort.");
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        // Display students after sorting
        displaySortedStudents();
    }

    private void quickSortStudents(int left, int right) {
        if (left < right) {
            int pi = partition(left, right);
            quickSortStudents(left, pi - 1);
            quickSortStudents(pi + 1, right);
        }
    }

    private int partition(int left, int right) {
        double pivot = students.get(right).getMark();
        int i = (left - 1);
        for (int j = left; j < right; j++) {
            if (students.get(j).getMark() <= pivot) {
                i++;
                Collections.swap(students, i, j);
            }
        }
        Collections.swap(students, i + 1, right);
        return i + 1;
    }

    private void selectionSortStudents() {
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (students.get(j).getMark() < students.get(min_idx).getMark()) {
                    min_idx = j;
                }
            }
            Collections.swap(students, min_idx, i);
        }
    }

    private void mergeSortStudents(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortStudents(left, mid);
            mergeSortStudents(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        ArrayList<Student> L = new ArrayList<>();
        ArrayList<Student> R = new ArrayList<>();

        for (int i = 0; i < n1; i++) {
            L.add(students.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            R.add(students.get(mid + 1 + j));
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L.get(i).getMark() <= R.get(j).getMark()) {
                students.set(k, L.get(i));
                i++;
            } else {
                students.set(k, R.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            students.set(k, L.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            students.set(k, R.get(j));
            j++;
            k++;
        }
    }

    private void displaySortedStudents() {
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void menu() {
        int choice;
        do {
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Sort Students");
            System.out.println("5. Search Student");
            System.out.println("6. Display Students");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    editStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    sortStudents();
                    break;
                case 5:
                    searchStudent();
                    break;
                case 6:
                    displayStudents();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
}

public class Main {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        manager.menu();
    }
}
