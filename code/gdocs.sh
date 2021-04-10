#!/bin/bash
rm ~/repos/ox-soa/lab-source/*.docx
rm ~/repos/ox-soa/lab-pdfs/*.pdf
rm ~/repos/ox-soa/presentation-source/*.pptx
rm ~/repos/ox-soa/presentation-pdfs/*.pdf
drive pull -no-prompt -explicitly-export -export docx -exports-dir ~/repos/ox-soa/lab-source/ -same-exports-dir ~/drive/ox-soa/lab-source/ 
drive pull -no-prompt -explicitly-export -export pdf -exports-dir ~/repos/ox-soa/lab-pdfs/ -same-exports-dir ~/drive/ox-soa/lab-source/ 
drive pull -no-prompt -explicitly-export -export pptx -exports-dir ~/repos/ox-soa/presentation-source/ -same-exports-dir ~/drive/ox-soa/presentation-source/ 
drive pull -no-prompt -explicitly-export -export pdf -exports-dir ~/repos/ox-soa/presentation-pdfs/ -same-exports-dir ~/drive/ox-soa/presentation-source/ 
cd ~/repos/ox-soa
git add .
git commit -m "updated slides and docs"
git push
