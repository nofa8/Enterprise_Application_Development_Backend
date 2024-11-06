package adgf.academics.ejbs;

import adgf.academics.entities.Subject;
import adgf.academics.exceptions.MyEntityExistsException;
import adgf.academics.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.annotation.PostConstruct;

import java.util.logging.Logger;

@Singleton
@Startup
public class ConfigBean {

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @EJB
    private StudentBean studentBean;

    @EJB
    private CourseBean courseBean;

    @EJB
    private SubjectBean subjectBean;


    @EJB
    private TeacherBean teacherBean;

    @EJB
    private AdministratorBean administratorBean;

    @PostConstruct
       public void populateDB() {

        try{
            courseBean.create("Contabilidade", 1);
            courseBean.create("Enfermagem", 2);
            courseBean.create("Professional McDonalds Employee", 3);
            courseBean.create("Marketing", 4);


            studentBean.create("primeiro", "adadsa", "Nome", "nome@sapo2.ded",4);
            studentBean.create("segundo", "adad2sa", "NomF", "nome@sapo1.ded",3);
            studentBean.create("terceiro", "adad3sa", "Nomd", "nome@sapo3.ded",2);
            studentBean.create("quarto", "adad4sa", "Nomr", "nome@sapo4.ded",1);
            studentBean.create("carnino", "adad4sas", "Nomrdsa", "ndsaome@sapo4.ded",1);

            subjectBean.create(2, "DAD", "2014/15", 3, 3);
            subjectBean.create(1, "DAE", "2014/15", 1, 1);
            subjectBean.create(3, "IS", "2014/15", 2, 4);
            subjectBean.create(4, "SI", "2014/15", 4, 2);
        //subjectBean.create(long code, String name, String schoolYear, int courseYear, Course course);

            teacherBean.create("TaboRa", "adadsaf1", "TaboraLa", "nome@sapo21.ded","RB Headquarters 1");
            teacherBean.create("Tabola", "adadsafff", "TaboraLas", "nome@sapo1221.ded","RB Headquarters 2");
            teacherBean.create("TaboDa", "adadsaff", "TaboraLaz", "nome@saposd21.ded","RB Headquarters 3");
            teacherBean.create("TaboTa", "adadsaf2", "TaboraLax", "nome@sapof21.ded","RB Headquarters 4");
            teacherBean.create("TaboJa", "adadsaf2sd", "TaboraLaximus", "nomedsa@sapof21.ded","RB Headquarters 5");


            administratorBean.create("admin1", "admin1", "admin1", "nome@admin.ded");
            administratorBean.create("admin2", "admin2", "admin2", "nome@admin2.ded");
            administratorBean.create("admin3", "admin3", "admin3", "nome@admin3.ded");
        } catch (Exception e){
            logger.severe(e.getMessage());
        }


        teacherBean.enrollTeacherInSubject("TaboRa",1);
        teacherBean.enrollTeacherInSubject("Tabola",2);
        teacherBean.enrollTeacherInSubject("TaboDa",3);
        teacherBean.enrollTeacherInSubject("TaboRa",3);
        teacherBean.enrollTeacherInSubject("Tabola",1);
        teacherBean.enrollTeacherInSubject("TaboDa",2);

        studentBean.enrollStudentInSubject("quarto", 1);
        studentBean.enrollStudentInSubject("terceiro", 4);
        studentBean.enrollStudentInSubject("segundo", 2);
        studentBean.enrollStudentInSubject("primeiro", 3);




    }
}
