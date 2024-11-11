// QuizClient.java
import java.io.*;
import java.net.*;

public class QuizClient {
    private static final String SERVER_ADDRESS = "localhost"; // Server IP 자동 설정
    private static final int PORT = 9012; // Port 9012번

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) { // Socket 생성
            System.out.println("Connected to the quiz server!"); // Server와 연결 시 출력

            // 텍스트 입출력 변수 설정
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            String question; // 퀴즈 내용
            int question_num = 0; // 퀴즈 번호
            
            while ((question = in.readLine()) != null) { // Server에서 퀴즈 내용 전송 받음
            	if (question.equals("END")) {
                    break; // "END" 메시지를 감지하면 루프 종료
                }
                System.out.println("\nQuestion [" + ++question_num + "] : " + question); // 문제 출제
                String answer = userInput.readLine(); // 답안
                System.out.print("Your answer : "); // 답안 입력
                out.println(answer); // Server로 답안 전송
                String feedback = in.readLine(); // 정답 여부
                System.out.println(feedback); // Server에서 정답 여부 받아서 출력
            }

            String comment; // 문제 이후 마무리 설명
            while ((comment = in.readLine()) != null) {
            	System.out.println("\n" + comment);
            }
            
            System.out.println("\nDisconnecting..."); // 연결 종료
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
