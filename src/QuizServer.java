// QuizServer.java
import java.io.*;
import java.net.*;

public class QuizServer {
    // 문제 및 정답
    private static final String[] QUESTIONS = {
        "What is the capital of Korea? (a) Paris (b) London (c) Seoul",
        "What is 1 + 2? (a) 2 (b) 3 (c) 4",
        "What is the name of the planet we live on? (a) Earth (b) Jupiter (c) Mars"
    };
    private static final String[] ANSWERS = {"c", "b", "a"};
    private static final int PORT = 9012; // Port 9012번

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) { // ServerSocket 생성
            System.out.println("Quiz Server is running... // (port#=" + PORT + ")\n "); // ServerSocket 생성 시 출력
            
            Socket clientSocket = serverSocket.accept(); // ServerSocket에 연결된지 확인
            System.out.println("Client connected!");

            // 텍스트 입출력 변수 설정
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            int score = 0; // 점수 0점 초기화

            // 퀴즈 출제
            for (int i = 0; i < QUESTIONS.length; i++) {
                out.println(QUESTIONS[i]); // Client로 퀴즈 전송
                String response = in.readLine(); // Client에서 답안 받음
                if (response.equalsIgnoreCase(ANSWERS[i])) { // 답안 맞으면
                    out.println("Correct!");
                    score += 10; // 점수 +10점
                } else { // 답안 틀리면
                    out.println("Incorrect! The correct answer was: " + ANSWERS[i]);
                }
            }
            
            out.println("END"); // Client에 퀴즈 종료 알림
            out.println("Quiz finished! Your total score is: " + score); // 최종 점수 Client로 전송
            
            // 5초 대기 후 연결 종료
            out.println("The connection will be terminated in 5 seconds.");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            clientSocket.close();
            System.out.println("Client disconnected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
