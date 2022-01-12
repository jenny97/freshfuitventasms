package com.trabajodegrado.freshfruitventas.utilidades;

public class EnvioCorreos {
	
//	@Autowired
//	private  JavaMailSender sender;
	
	
	public  boolean enviarCorreo(String texto, String correo, String asunto) {
		boolean enviado = false;
//		MimeMessage message = sender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message);		
//		try {
//			helper.setTo(correo);
//			helper.setText(texto, true);
//			helper.setSubject(asunto);
//			sender.send(message);
//			enviado = true;
//			System.out.println("Mail enviado!");
//		} catch (MessagingException e) {
//			System.err.println("Hubo un error al enviar el mail: {}");
//		}
		return enviado;
	}
	

}
