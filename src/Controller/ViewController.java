package Controller;

import Model.Student;
import View.RegistrationView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewController {
    RegistrationView theView;
    StudentController theStudentController;
    StudentListController theStudentListController;
    CourseController theCourseController;

    public ViewController(RegistrationView theView, StudentController studentController, StudentListController studentListController, CourseController courseController) {
        this.theView = theView;
        this.theStudentController = studentController;
        this.theStudentListController = studentListController;
        this.theCourseController = courseController;

        theView.addSearchButtonListener(new SearchButtonListener());
        theView.addLoginButtonListener(new LoginButtonListener());

    }

    class LoginButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if(!theView.validateStudentId()){
                return;
            }

            String name = theView.getStudetnName();
            String id = theView.getStudentID();

            if(theStudentListController.validateStudent(name, Integer.parseInt(id)) == null) {
                theView.setOutputAreaText("That is not a student in our Database.");
            }
            else {
                theStudentController.setCurrentStudent(theStudentListController.validateStudent(name, Integer.parseInt(id)));
                theView.updateLogin();
            }
        }
    }

    class SearchButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            // exit if inputs are invalid
            if(!theView.validateSectionNumber()){
                return;
            }

            int option = theView.getSelectedOption();
            String name = theView.getStudetnName();
            String id = theView.getStudentID();
            String courseName = theView.getCourseName();
            String courseNum = theView.getCourseNum();
            String secNum = theView.getSecNum();
            String outputString = "";

            //find the student using getStudent() method
            outputString = "Hello " + theStudentController.getCurrentStudent() + "\n";

            // talk to back end using this input...
            // and get a string back to output to the user
            switch (option + 1) {
                // search for course
                case 1:
                    outputString = theCourseController.printCourse(courseName, courseNum);
                    break;

                // add a course
                case 2:
                    outputString += theStudentController.addCourse(theCourseController.searchForCourse(courseName, courseNum),
                                                                    courseName, courseNum, Integer.parseInt(secNum));
                    break;

                // Remove a course
                case 3:
                    outputString += theStudentController.removeCourse(courseName, courseNum, Integer.parseInt(secNum));
                    break;

                // View all courses
                case 4:
                    outputString = "Course Catalogue:\n\n" + theCourseController.printAllCourses();
                    break;

                // View all courses take by student
                case 5:
                    outputString += "Your Registered Courses: \n\n";
                    outputString += theStudentController.getStudentCourses();
                    break;

                default:
                    outputString = "Error!";
                    break;
            }

            theView.setOutputAreaText(outputString);

        }
    }

}


