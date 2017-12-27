package pl.exam.app.examapp.configuration;

//public class CustomAccessDeniedHandler implements AccessDeniedHandler
//{
//	public static final Logger LOG = Logger.getLogger(CustomAccessDeniedHandler.class);
//
//	@Override
//	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc)
//			throws IOException, ServletException
//	{
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if (auth != null)
//			LOG.warn("User: " + auth.getName() + " attempted to access the protected URL: " + request.getRequestURI());
//		
//		response.sendRedirect(request.getContextPath() + "/accessDenied");
//	}
//
//}