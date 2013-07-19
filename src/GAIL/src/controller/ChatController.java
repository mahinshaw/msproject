package GAIL.src.controller;

import GAIL.src.frame.ChatPanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatController implements Runnable {

	private ApplicationController applicationController;

	private String ipAddress;
	private int portNumber;
	static ServerSocket serverSocket;
	static Socket socket;
	static BufferedReader input;
	static PrintWriter output;
	static StringBuffer inputBuffer = new StringBuffer("");
	static StringBuffer outputBuffer = new StringBuffer("");
	static ChatPanel chatPanel;
	Thread thread;

	public ChatController(ApplicationController applicationController) {
		this.applicationController = applicationController;
		final ChatController chatController = this;
		chatPanel = new ChatPanel(chatController);
	}

	public boolean isChatOn() {
		return isRunning;
	}

	public void establishConnection(int portNumber, String ipAddress) {
		this.portNumber = portNumber;
		this.ipAddress = ipAddress;
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public void disconnect() {
		isRunning = false;
	}

	public void sendMessageToHost(String string) {
		outputBuffer.append(string + "\n");
		applicationController.appendToChatSession("CHAT: Participant: "
				+ string);
	}

	final String CLEAR_WORKSPACE = "CLEAR_WORKSPACE";
	final String RESTART_SESSION = "RESTART_SESSION";
	final String SAVE_SESSION = "SAVE_SESSION";
	private String CLEAR_DIALOG = "CLEAR_DIALOG";
	final String EXIT_PROGRAM = "EXIT_PROGRAM";
	final String ENABLE_SUBMIT = "ENABLE_SUBMIT";
	final String DISABLE_SUBMIT = "DISABLE_SUBMIT";
	public final static String SUBMIT_PRESSED = "SUBMIT_PRESSED";
	public static String SUBMIT_ARGUMENT = "SUBMIT_ARGUMENT";

	public void receiveMessageFromHost() {
		String string = null;
		try {
			string = input.readLine();
		} catch (IOException e) {
		}
		if (string.equals(CLEAR_WORKSPACE)) {
			applicationController.createAndShowClearWorkspacePrompt();
			return;
		}
		if (string.equals(SUBMIT_ARGUMENT)) {
			System.out.println("EEE");
			applicationController.update();
			// applicationController.appendToChatSession("CHAT: Helper: " +
			// string);
			return;
		}
		if (string.equals(DISABLE_SUBMIT)) {
			applicationController.disableSubmission();
			System.out.println("DISAB");
			return;

		} else if (string.equals(ENABLE_SUBMIT)) {
			applicationController.enableSubmission();
			System.out.println("ENABLE");
			return;
		} else if (string.equals(CLEAR_DIALOG)) {
			chatPanel.clearText();
			return;
		} else if (string.equals(RESTART_SESSION)) {
			applicationController.reset();
		} else if (string.equals(SAVE_SESSION)) {
			applicationController.saveFinalArgument();
		} else if (string.equals(EXIT_PROGRAM)) {
			closeChat();
			System.exit(0);
		}
		chatPanel.appendToTextArea(" Helper : " + string);
		applicationController.appendToChatSession("CHAT: Helper: " + string);
	}

	public ChatPanel getChatPanel() {
		return chatPanel;
	}

	boolean isRunning;

	@Override
	public void run() {
		while (isRunning) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException ignored) { }

			try {
				if (socket == null) {
					try {
						socket = new Socket(ipAddress, portNumber);
					} catch (IOException e) {
					}
					if (socket != null) {
						input = new BufferedReader(new InputStreamReader(
								socket.getInputStream()));
						output = new PrintWriter(socket.getOutputStream(), true);
					}
				} else {
					if (outputBuffer.length() > 0) {
						output.print(outputBuffer);
						output.flush();
						outputBuffer.setLength(0);
					}
					if (input.ready()) {
						receiveMessageFromHost();
					}

				}
			} catch (IOException e) {
				System.err.println("Chat IOException");
			}
		}
	}

	void closeChat() {
		try {
			socket.close();
			serverSocket.close();
		} catch (IOException e1) {
		}
	}
}