package Refrigerator_Client;
import java.io.IOException;

import OCSF.Client.ChatIF;
import OCSF.Common.ChatClient;

public class Packet implements ChatIF {

	/* p@ gui ui로 바꿨습니다 */

	private ChatClient client;
	private UI ui;

	public Packet(String host, int port) {
		try {
			client = new ChatClient(host, port, this);
			ui = new UI(client);

		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!"
					+ " Terminating client.");
			System.exit(1);
		}
	}

	public void accept() {
		try {
			// BufferedReader fromConsole = new BufferedReader(new
			// InputStreamReader(System.in));
			// String message;
			int result = 0;
			int Trying = 0;
			System.out.println("Welcome to HW_Refrigerator_System!");
			// [stage1] Login
			while(true)
			{
				ui.Login();
				if(ui.GetStatus() != UserStatus.MENU)
				{
					Trying++;
					System.out.println("Trying "+Trying+"/3");
				}
				if(ui.GetStatus() == UserStatus.MENU)
				{
					Trying = 0;
					// [stage2] Get Message
					ui.GetMessage();
					while (true) {
						// [stage3] show FoodList
						result = ui.Menu();
						if (result == 0)
							break;
					}
					System.exit(0);
				}
				
				if(Trying == 3)
				{
					System.out.println("Unable to Login to Server after 3 attempts. exit.");
					System.exit(0);
				}
			}
		} catch (Exception ex) {
			System.out.println("Unexpected error while reading from console!");
		}
	}

	/* p@ 문자열 말고 객체를 출력하면 좋지 않을까요? */
	public void display(String message) {
		System.out.println("> " + message);

		String[] Packet = message.split("_");
		String cmd = Packet[0];
		// Login 이미 로그인
		if(cmd.equals("ALREADY"))
		{
			System.out.println("Already Loged in User");
			System.out.println(">Login Fail!!!");
			ui.SetStatus(UserStatus.LOGIN_FAIL);
		}
		// Login 부분
		// if (cmd.equals("LOGIN")) {
		else if (Packet[0].equals("LOGIN")) {
			// Login fail
			if (Packet[1].equals("FALSE")) {
				System.out.println(">Login Fail!!!");
				ui.SetStatus(UserStatus.LOGIN_FAIL);
				//ui.Login();
			}
			// Login success( Status change)
			else if (Packet[1].equals("TRUE")) {
				System.out.println(">Login Sucess!!!");
				ui.SetStatus(UserStatus.MENU);
				if (Packet[2].equals("ADMINISTRATOR"))
				{
					ui.setAuthority(true);
					ui.setName(Packet[3]);
				}
				else if (Packet[2].equals("NORMALUSER"))
				{
					ui.setAuthority(false);
					ui.setName(Packet[3]);
				}
			}
		}
		// FOOD 부분
		else if (cmd.equals("FOOD")) {
			String cmd2 = Packet[1];
			if (cmd2.equals("MODIFY")) {
				String result = Packet[2];
				if (result.equals("TRUE")) {
					System.out.println("Food Modify Success");
					ui.SetStatus(UserStatus.DONE);
				} else if (result.equals("FALSE")) {
					System.out.println("Food Modify Fail");
					ui.SetStatus(UserStatus.DONE);
				}
			} else if (cmd2.equals("DELETE")) {
				String result = Packet[2];
				if (result.equals("TRUE")) {
					System.out.println("Food delete Success");
					ui.SetStatus(UserStatus.DONE);
				} else if (result.equals("FALSE")) {
					System.out.println("Food delete Fail");
					ui.SetStatus(UserStatus.DONE);
				}
			} else if (cmd2.equals("REGISTER")) {
				String result = Packet[2];
				if (result.equals("TRUE")) {
					System.out.println("Food Register Success");
					ui.SetStatus(UserStatus.DONE);
				} else if (result.equals("FALSE")) {
					System.out.println("Food Register Fail");
					ui.SetStatus(UserStatus.DONE);
				}
			}
			else if (cmd2.equals("SEARCH"))
			{
				System.out.println(Packet[2]);
				ui.SetStatus(UserStatus.DONE);
			}
			else {
				System.out.println("-----Food List-----");
				if(cmd2.equals("Refrigerator is empty"))
				{
					System.out.println("Go to menu");
					ui.SetStatus(UserStatus.FOOD_EMPTY);
				}
				else
				{
					System.out.println(cmd2);
					ui.SetStatus(UserStatus.FOOD_LOAD);
				}
			}
		}
		// USER 부분
		else if (cmd.equals("USER")) {
			String cmd1 = Packet[1];
			String cmd2 = Packet[2];

			if (cmd1.equals("MODIFY")) {
				if (cmd2.equals("TRUE")) {
					System.out.println("USER MODIFY Sucess!!!");
					ui.SetStatus(UserStatus.DONE);
				}
				if (cmd2.equals("FALSE")) {
					System.out.println("USER MODIFY FAIL !!!");
					ui.SetStatus(UserStatus.DONE);
				}
			} else if (cmd1.equals("DELETE")) {
				if (cmd2.equals("TRUE")) {
					System.out.println("USER DELETE Sucess!!!");
					ui.SetStatus(UserStatus.DONE);
				}
				if (cmd2.equals("FALSE")) {
					System.out.println("USER DELETE FAIL !!!");
					ui.SetStatus(UserStatus.DONE);
				}
			} else if (cmd1.equals("REGISTER")) {
				if (cmd2.equals("TRUE")) {
					System.out.println("USER REGISTER Sucess!!!");
					ui.SetStatus(UserStatus.DONE);
				}
				if (cmd2.equals("FALSE")) {
					System.out.println("USER REGISTER FAIL !!!");
					ui.SetStatus(UserStatus.DONE);
				}
			} else if (cmd1.equals("INFO")) {
				if (Packet[4].equals("TRUE")) {
					System.out.println("USER REGISTER Sucess!!!");
					ui.SetStatus(UserStatus.DONE);
					ui.setId(Packet[2]);
					ui.setName(Packet[3]);
				} else if (Packet[4].equals("FALSE")) {
					System.out.println("USER REGISTER FAIL !!!");
					ui.SetStatus(UserStatus.DONE);
				}
				ui.SetStatus(UserStatus.DONE);
			}
			else
			{
				System.out.println(Packet[2]);
				ui.SetStatus(UserStatus.USER_LOAD);
			}
		}
		// 메세지 부분
		else if (cmd.equals("MSG")) {
			String cmd2 = Packet[1];
			if (cmd2.equals("SHOW")) {
				String Mlist = Packet[2];
				System.out.println("-----Message List-----");
				System.out.println(Mlist);
				ui.SetStatus(UserStatus.MSG_LOAD);
			} else if (cmd2.equals("MEMO")) {
				String result = Packet[2];
				if (result.equals("TRUE")) {
					System.out.println("Memo Success");
					ui.SetStatus(UserStatus.DONE);
				}
				
				else if (result.equals("FALSE")) {
					System.out.println("Memo fail");
					ui.SetStatus(UserStatus.DONE);
				}
				ui.SetStatus(UserStatus.DONE);
			} else if (cmd2.equals("DELETE")) {
				String result = Packet[2];

				// Old message delete success
				if (result.equals("TRUE")) {
					System.out.println("Message Delete Success");
					ui.SetStatus(UserStatus.DONE);
				}
				// delete fail
				else if (result.equals("FALSE")) {
					System.out.println("Message Delete fail");
					ui.SetStatus(UserStatus.DONE);
				}
				ui.SetStatus(UserStatus.DONE);
			}
		}
	}
}
