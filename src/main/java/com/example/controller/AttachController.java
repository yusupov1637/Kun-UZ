package com.example.controller;

public class AttachController {

//    @Autowired
//    private AttachService attachService;
//
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadOld(@RequestParam("file") MultipartFile file) {
////        String fileName = attachService.saveToSystemOld(file);
////        return ResponseEntity.ok().body(fileName);
//        return null;
//    }
//
//    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
//    public byte[] open(@PathVariable("fileName") String fileName) {
//        if (fileName != null && fileName.length() > 0) {
//            try {
//                return this.attachService.loadImage(fileName);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return new byte[0];
//            }
//        }
//        return null;
//    }
//
//    @GetMapping(value = "/open_general/{fileName}", produces = MediaType.ALL_VALUE)
//    public byte[] open_general(@PathVariable("fileName") String fileName) {
//        return attachService.open_general(fileName);
//    }
//
//    @GetMapping("/download/{fineName}")
//    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
//        Resource file = attachService.download(fileName);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
//        AttachDTO dto = attachService.saveToSystem(file);
//        return ResponseEntity.ok().body(dto);
//    }
}
