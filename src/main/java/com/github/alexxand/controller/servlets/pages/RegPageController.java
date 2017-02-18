package com.github.alexxand.controller.servlets.pages;

import com.github.alexxand.model.BasicManagerInf;
import com.github.alexxand.model.CompanyInf;
import com.github.alexxand.model.ManagerInf;
import com.github.alexxand.model.Photo;
import com.github.alexxand.model.validation.FormValidation;
import com.github.alexxand.model.validation.Validator;
import com.github.alexxand.service.ManagerService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Singleton
public class RegPageController extends HttpServlet {

    private static final String ROOT_URI = "/";
    private static final String COMPANY_URI = "/company";
    private static final String PHOTO_URI = "/photo";

    private static final String ROOT_VIEW = "/reg/index.jsp";
    private static final String COMPANY_VIEW = "/reg/company.jsp";
    private static final String PHOTO_VIEW = "/reg/photo.jsp";

    private static final String MANAGER_INF_ATTR = "managerInf";

    private final ManagerService managerService;
    private final Validator validator;

    @Inject
    public RegPageController(ManagerService managerService, Validator validator) {
        this.managerService = managerService;
        this.validator = validator;
    }

    private static String getAdditionalURI(HttpServletRequest req) {
        String servletPath = req.getServletPath();
        int lastSlashIndex = servletPath.lastIndexOf("/");
        String additionalPath = servletPath.substring(lastSlashIndex);
        if (lastSlashIndex == 0)
            additionalPath = "/";
        return additionalPath;
    }

    private static String getRootLocation(HttpServletRequest req) {
        return req.getContextPath() + "/reg";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String additionalURI = getAdditionalURI(req);
        final ManagerInf managerInf = (ManagerInf) req.getSession().getAttribute(MANAGER_INF_ATTR);
        final String rootLocation = getRootLocation(req);

        if (!additionalURI.equals(ROOT_URI) && managerInf == null) {
            resp.sendRedirect(rootLocation);
            return;
        }

        switch (additionalURI) {
            case ROOT_URI:
                req.getServletContext().getRequestDispatcher("/WEB-INF" + ROOT_VIEW).forward(req, resp);
                break;
            case COMPANY_URI:
                req.getServletContext().getRequestDispatcher("/WEB-INF" + COMPANY_VIEW).forward(req, resp);
                break;
            case PHOTO_URI:
                req.getServletContext().getRequestDispatcher("/WEB-INF" + PHOTO_VIEW).forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final ManagerInf managerInf;
        FormValidation validation;
        final String rootLocation = getRootLocation(req);

        String additionalURI = getAdditionalURI(req);

        switch (additionalURI) {
            case ROOT_URI:
                BasicManagerInf baseInf = BasicManagerInf.builder().
                        name(req.getParameter("name")).
                        lastName(req.getParameter("lastName")).
                        email(req.getParameter("email")).
                        password(req.getParameter("password")).
                        build();

                validation = validator.validate(baseInf);

                if (!validation.isValid()) {
                    req.setAttribute("validation", validation);
                    req.setAttribute("data",baseInf);
                    req.getServletContext().getRequestDispatcher("/WEB-INF" + ROOT_VIEW).forward(req, resp);
                    break;
                }

                managerInf = managerService.stash(baseInf);
                if (managerInf == null) {
                    validation.getErrors().put("EMAIL_EXISTS",true);
                    req.setAttribute("validation",validation);
                    req.setAttribute("data",baseInf);
                    req.getServletContext().getRequestDispatcher("/WEB-INF" + ROOT_VIEW).forward(req, resp);
                    break;
                }

                /*System.out.println(managerInf.getEmail());
                System.out.println(managerInf.getPasswordHash());
                System.out.println(managerInf.getFullName());*/

                req.getSession().setAttribute(MANAGER_INF_ATTR, managerInf);
                resp.sendRedirect(rootLocation + COMPANY_URI);

                break;
            case COMPANY_URI:
                CompanyInf companyInf = CompanyInf.builder().
                        company(req.getParameter("company")).
                        position(req.getParameter("position")).
                        build();

                validation = validator.validate(companyInf);

                if (!validation.isValid()) {
                    req.setAttribute("validation", validation);
                    req.setAttribute("data",companyInf);
                    req.getServletContext().getRequestDispatcher("/WEB-INF" + COMPANY_VIEW).forward(req, resp);
                    break;
                }

                managerInf = (ManagerInf) req.getSession().getAttribute(MANAGER_INF_ATTR);
                managerInf.setCompanyInf(companyInf);
                req.getSession().setAttribute(MANAGER_INF_ATTR, managerInf);

                resp.sendRedirect(rootLocation + PHOTO_URI);
                break;
            case PHOTO_URI:

                if (!ServletFileUpload.isMultipartContent(req)) {
                    //log warn
                    //alert
                    req.getServletContext().getRequestDispatcher("/WEB-INF" + PHOTO_VIEW).forward(req, resp);
                    break;
                }

                DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);

                try {
                    final InputStream photoInputStream;
                    final long photoSize;
                    final Photo photo;
                    final List<FileItem> items = upload.parseRequest(req);
                    final FileItem item;

                    if (items.size() == 0 || items.size() > 1){
                        //log warn
                        //alert
                        //forward
                        break;
                    }

                    item = items.get(0);
                    if (!item.getFieldName().equals("photo")) {
                        //log warn
                        //alert
                        //forward
                        break;
                    }

                    photoInputStream = item.getInputStream();
                    photoSize = item.getSize();
                    if (!(Integer.MIN_VALUE <= photoSize && photoSize <= Integer.MAX_VALUE)){
                        //alert
                        //forward
                        break;
                    }


                    photo = Photo.builder().
                            inputStream(photoInputStream).
                            size((int)photoSize).
                            build();

                    managerInf = (ManagerInf) req.getSession().getAttribute(MANAGER_INF_ATTR);

                    managerService.add(managerInf, photo);

                    resp.sendRedirect(req.getContextPath() + "/profile");
                } catch (FileUploadException e) {
                    //log warn
                    //alert
                }

                break;

        }
    }
}
