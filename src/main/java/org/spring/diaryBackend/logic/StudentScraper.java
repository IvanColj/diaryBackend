package org.spring.diaryBackend.logic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.spring.diaryBackend.model.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentScraper {

    public static List<Student> fetchStudents(Long groupNumber) throws IOException {
        String url = "https://portal.novsu.ru/search/groups/r.2500.p.search.g.1991/i.2500/?page=search&grpname=" + groupNumber;
        Document doc = Jsoup.connect(url).get();

        Element table = doc.selectFirst("table.viewtable");
        List<Student> students = new ArrayList<>();

        if (table != null) {
            Elements rows = table.select("tr");
            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements cells = row.select("td");
                if (cells.size() >= 3) {
                    String fio = cells.get(1).text().trim();
                    String[] fioParts = splitFio(fio);

                    Student student = new Student();
                    student.setSurname(fioParts[0]);
                    student.setName(fioParts[1]);
                    student.setLastName(fioParts[2]);
                    student.setNumberGroup(groupNumber);

                    students.add(student);
                }
            }
        } else {
            System.out.println("Таблица студентов не найдена на странице.");
        }

        return students;
    }

    private static String[] splitFio(String fio) {
        String[] parts = fio.split("\\s+");
        String surname = parts.length > 0 ? parts[0] : "";
        String name = parts.length > 1 ? parts[1] : "";
        String lastName = parts.length > 2 ? parts[2] : "";
        return new String[]{surname, name, lastName};
    }

    public static void main(String[] args) {
        try {
            Long groupNumber = 2996L;
            List<Student> students = fetchStudents(groupNumber);
            for (Student s : students) {
                System.out.printf("Фамилия: %s, Имя: %s, Отчество: %s, Группа: %d%n",
                        s.getSurname(), s.getName(), s.getLastName(), s.getNumberGroup());
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке страницы: " + e.getMessage());
        }
    }
}

