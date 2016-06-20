/*
 * This program uses a dictionary of E. coli K-12 MG1655 genes to find sites at which deletion oligos can be designed with MODEST (http://modest.biosustain.dtu.dk)
 * 
 * Code written by 
 *     Trevor Birenbaum (University of Miami '20)
 *     James McLaughlin (California Institute of Technology '20)
 * Special thanks
 *     Dr. Richard S. Myers (Department of Biochemistry and Molecular Biology, University of Miami Miller School of Medicine)
 *     Dr. Thomas K. Harris (Department of Biochemistry and Molecular Biology, University of Miami Miller School of Medicine)
 * 
 * Date created: 06/11/16
 * Last updated: 06/20/16
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class OligoDeletionSites {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		
		//dictionary of genes
		Map<String, String> map = new HashMap<String, String>();
		map.put("clpA", "ATG CTC AAT CAA GAA CTG GAA CTC AGT TTA AAT ATG GCT TTC GCC AGA GCG CGC GAG CAC CGT CAT GAG TTT ATG ACC GTC GAG CAC TTG TTA CTG GCG CTG CTC AGT AAC CCA TCT GCC CGG GAG GCG CTG GAA GCG TGT TCT GTG GAT TTG GTT GCG CTC CGT CAG GAA CTG GAA GCC TTT ATT GAA CAA ACC ACA CCC GTT CTG CCT GCC AGT GAA GAG GAG CGC GAC ACA CAG CCG ACG CTG AGT TTT CAG CGT GTA CTG CAA CGT GCG GTC TTC CAT GTC CAG TCC TCC GGT CGC AAT GAG GTT ACC GGT GCA AAC GTT CTG GTC GCT ATC TTT AGC GAA CAG GAG TCG CAG GCG GCA TAT CTG TTG CGT AAA CAT GAA GTC AGC CGT CTC GAT GTG GTG AAC TTT ATC TCT CAT GGC ACG CGT AAA GAC GAG CCG ACA CAG TCT TCT GAT CCT GGC AGC CAG CCA AAC AGC GAA GAA CAA GCT GGT GGG GAG GAA CGT ATG GAG AAT TTC ACG ACG AAC CTG AAT CAG CTT GCG CGC GTG GGC GGA ATC GAC CCA CTG ATT GGT CGT GAG AAG GAG CTG GAG CGT GCT ATT CAG GTT CTC TGC CGT CGC CGT AAA AAC AAC CCG CTG CTG GTG GGG GAA TCT GGT GTC GGT AAA ACC GCG ATT GCG GAA GGT CTT GCC TGG CGA ATT GTT CAG GGC GAT GTG CCG GAA GTG ATG GCT GAC TGT ACG ATT TAC TCT CTC GAT ATC GGT TCT CTG TTA GCG GGC ACA AAA TAT CGC GGC GAC TTT GAA AAA CGT TTT AAA GCG TTG CTC AAG CAG CTG GAG CAG GAC ACT AAC AGC ATC CTG TTT ATT GAT GAG ATC CAC ACC ATT ATC GGT GCG GGT GCA GCG TCT GGT GGT CAG GTC GAT GCG GCT AAC CTA ATC AAA CCG TTG CTC TCC AGC GGT AAA ATT CGT GTA ATT GGT TCG ACA ACC TAT CAG GAG TTC AGC AAC ATT TTC GAG AAA GAC CGT GCT CTG GCG CGT CGC TTC CAG AAA ATT GAT ATT ACT GAA CCG TCG ATC GAA GAA ACT GTT CAA ATC ATC AAT GGC CTG AAA CCG AAG TAT GAA GCG CAC CAC GAC GTG CGT TAT ACC GCA AAA GCG GTG CGT GCG GCG GTA GAG CTG GCG GTG AAA TAC ATT AAC GAT CGT CAT CTG CCG GAT AAA GCC ATT GAT GTT ATC GAC GAA GCG GGC GCT CGC GCA CGC CTG ATG CCG GTA AGC AAA CGC AAG AAA ACC GTT AAT GTG GCG GAT ATT GAG TCC GTG GTG GCC CGT ATT GCA CGC ATT CCA GAG AAG AGT GTT TCT CAG AGT GAT CGT GAT ACC CTG AAA AAC CTC GGC GAT CGC TTG AAA ATG CTG GTC TTC GGT CAG GAT AAA GCC ATT GAG GCG CTG ACT GAA GCC ATT AAG ATG GCG CGT GCA GGT TTA GGT CAC GAA CAT AAA CCG GTT GGT TCG TTC CTG TTT GCC GGC CCT ACC GGG GTC GGG AAA ACA GAG GTG ACG GTA CAG CTT TCG AAA GCT TTG GGC ATT GAG CTT CTG CGC TTT GAT ATG TCC GAG TAT ATG GAA CGC CAT ACC GTC AGC CGT CTT ATT GGT GCG CCT CCG GGA TAC GTT GGT TTT GAT CAG GGC GGT TTG CTG ACT GAT GCG GTC ATC AAG CAT CCA CAT GCG GTG CTG CTG CTG GAC GAA ATC GAG AAA GCG CAC CCG GAC GTG TTC AAT ATT CTG TTG CAG GTG ATG GAT AAC GGT ACG CTG ACC GAT AAC AAC GGA CGC AAA GCA GAC TTC CGT AAC GTG GTG CTG GTG ATG ACC ACC AAC GCC GGG GTA CGG GAA ACT GAG CGC AAA TCC ATT GGT CTT ATC CAC CAG GAT AAC AGC ACC GAT GCG ATG GAA GAG ATC AAG AAG ATC TTT ACA CCG GAA TTC CGT AAC CGT CTC GAC AAC ATT ATC TGG TTT GAT CAT CTG TCA ACC GAC GTG ATC CAT CAG GTG GTG GAT AAA TTC ATC GTC GAG TTG CAG GTT CAG CTG GAT CAG AAA GGT GTT TCT CTG GAA GTG AGC CAG GAA GCG CGT AAC TGG CTG GCC GAG AAA GGT TAC GAC CGG GCA ATG GGC GCT CGT CCG ATG GCG CGT GTC ATC CAG GAC AAC CTG AAA AAA CCG CTC GCC AAC GAA CTG CTG TTT GGT TCG CTG GTG GAC GGC GGT CAG GTC ACC GTC GCG CTG GAT AAA GAG AAA AAT GAG CTG ACT TAC GGA TTC CAG AGT GCA CAA AAG CAC AAG GCG GAA GCA GCG CAT TAA");
		map.put("clpB", "ATG CGT CTG GAT CGT CTT ACT AAT AAA TTC CAG CTT GCT CTT GCC GAT GCC CAA TCA CTT GCA CTC GGG CAC GAC AAC CAA TTT ATC GAA CCA CTT CAT TTA ATG AGC GCC CTG CTG AAT CAG GAA GGG GGT TCG GTT AGT CCT TTA TTA ACA TCC GCT GGC ATA AAT GCT GGC CAG TTG CGC ACA GAT ATC AAT CAG GCA TTA AAT CGT TTA CCG CAG GTT GAA GGT ACT GGT GGT GAT GTC CAG CCA TCA CAG GAT CTG GTG CGC GTT CTT AAT CTT TGC GAC AAG CTG GCG CAA AAA CGT GGT GAT AAC TTT ATC TCG TCA GAA CTG TTC GTT CTG GCG GCA CTT GAG TCT CGC GGC ACG CTG GCC GAC ATC CTG AAA GCA GCA GGG GCG ACC ACC GCC AAC ATT ACT CAA GCG ATT GAA CAA ATG CGT GGA GGT GAA AGC GTG AAC GAT CAA GGT GCT GAA GAC CAA CGT CAG GCT TTG AAA AAA TAT ACC ATC GAC CTT ACC GAA CGA GCC GAA CAG GGC AAA CTC GAT CCG GTG ATT GGT CGT GAT GAA GAA ATT CGC CGT ACC ATT CAG GTG CTG CAA CGT CGT ACT AAA AAT AAC CCG GTA CTG ATT GGT GAA CCC GGC GTC GGT AAA ACT GCC ATC GTT GAA GGT CTG GCG CAG CGT ATT ATC AAC GGC GAA GTG CCG GAA GGG TTG AAA GGC CGC CGG GTA CTG GCG CTG GAT ATG GGC GCG CTG GTG GCT GGG GCG AAA TAT CGC GGT GAG TTT GAA GAA CGT TTA AAA GGC GTG CTT AAC GAT CTT GCC AAA CAG GAA GGC AAC GTC ATC CTA TTT ATC GAC GAA TTA CAT ACC ATG GTC GGC GCG GGT AAA GCC GAT GGC GCA ATG GAC GCC GGA AAC ATG CTG AAA CCG GCG CTG GCG CGT GGT GAA TTG CAC TGC GTA GGT GCC ACG ACG CTT GAC GAA TAT CGC CAG TAC ATT GAA AAA GAT GCT GCG CTG GAA CGT CGT TTC CAG AAA GTG TTT GTT GCC GAG CCT TCT GTT GAA GAT ACC ATT GCG ATT CTG CGT GGC CTG AAA GAA CGT TAC GAA TTG CAC CAC CAT GTG CAA ATT ACT GAC CCG GCA ATT GTT GCA GCG GCG ACG TTG TCT CAT CGC TAC ATT GCT GAC CGT CAG CTG CCG GAT AAA GCC ATC GAC CTG ATC GAT GAA GCA GCA TCC AGC ATT CGT ATG CAG ATT GAC TCA AAA CCA GAA GAA CTC GAC CGA CTC GAT CGT CGT ATC ATC CAG CTC AAA CTG GAA CAA CAG GCG TTA ATG AAA GAG TCT GAT GAA GCC AGT AAA AAA CGT CTG GAT ATG CTC AAC GAA GAA CTG AGC GAC AAA GAA CGT CAG TAC TCC GAG TTA GAA GAA GAG TGG AAA GCA GAG AAG GCA TCG CTT TCT GGT ACG CAG ACC ATT AAA GCG GAA CTG GAA CAG GCG AAA ATC GCT ATT GAA CAG GCT CGC CGT GTG GGG GAC CTG GCG CGG ATG TCT GAA CTG CAA TAC GGC AAA ATC CCG GAA CTG GAA AAG CAA CTG GAA GCC GCA ACG CAG CTC GAA GGC AAA ACT ATG CGT CTG TTG CGT AAT AAA GTG ACC GAC GCC GAA ATT GCT GAA GTG CTG GCG CGT TGG ACG GGG ATT CCG GTT TCT CGC ATG ATG GAA AGC GAG CGC GAA AAA CTG CTG CGT ATG GAG CAA GAA CTG CAC CAT CGC GTA ATT GGT CAG AAC GAA GCG GTT GAT GCG GTA TCT AAC GCT ATT CGT CGT AGC CGT GCG GGG CTG GCG GAT CCA AAT CGC CCG ATT GGT TCA TTC CTG TTC CTC GGC CCA ACT GGT GTG GGG AAA ACA GAG CTT TGT AAG GCG CTG GCG AAC TTT ATG TTT GAT AGC GAC GAG GCG ATG GTC CGT ATC GAT ATG TCC GAG TTT ATG GAG AAA CAC TCG GTG TCT CGT TTG GTT GGT GCG CCT CCG GGA TAT GTC GGT TAT GAA GAA GGT GGC TAC CTG ACC GAA GCG GTG CGT CGT CGT CCG TAT TCC GTC ATC CTG CTG GAT GAA GTG GAA AAA GCG CAT CCG GAT GTC TTC AAC ATT CTG TTG CAG GTA CTG GAT GAT GGG CGT CTG ACT GAC GGG CAA GGG AGA ACG GTC GAC TTC CGT AAT ACG GTC GTC ATT ATG ACC TCT AAC CTC GGT TCC GAT CTG ATT CAG GAA CGC TTC GGT GAA CTG GAT TAT GCG CAC ATG AAA GAG CTG GTG CTC GGT GTG GTA AGC CAT AAC TTC CGT CCG GAA TTC ATT AAC CGT ATC GAT GAA GTG GTG GTC TTC CAT CCG CTG GGT GAA CAG CAC ATT GCC TCG ATT GCG CAG ATT CAG TTG AAA CGT CTG TAC AAA CGT CTG GAA GAA CGT GGT TAT GAA ATC CAC ATT TCT GAC GAG GCG CTG AAA CTG CTG AGC GAG AAC GGT TAC GAT CCG GTC TAT GGT GCA CGT CCT CTG AAA CGT GCA ATT CAG CAG CAG ATC GAA AAC CCG CTG GCA CAG CAA ATA CTG TCT GGT GAA TTG GTT CCG GGT AAA GTG ATT CGC CTG GAA GTT AAT GAA GAC CGG ATT GTC GCC GTC CAG TAA");
		map.put("clpP", "ATG TCA TAC AGC GGC GAA CGA GAT AAC TTT GCA CCC CAT ATG GCG CTG GTG CCG ATG GTC ATT GAA CAG ACC TCA CGC GGT GAG CGC TCT TTT GAT ATC TAT TCT CGT CTA CTT AAG GAA CGC GTC ATT TTT CTG ACT GGC CAG GTT GAA GAC CAC ATG GCT AAC CTG ATT GTG GCG CAG ATG CTG TTC CTG GAA GCG GAA AAC CCA GAA AAA GAT ATC TAT CTG TAC ATT AAC TCC CCA GGC GGG GTG ATC ACT GCC GGG ATG TCT ATC TAT GAC ACC ATG CAG TTT ATC AAG CCT GAT GTC AGC ACC ATC TGT ATG GGC CAG GCG GCC TCG ATG GGC GCT TTC TTG CTG ACC GCA GGG GCA AAA GGT AAA CGT TTT TGC CTG CCG AAT TCG CGC GTG ATG ATT CAC CAA CCG TTG GGC GGC TAC CAG GGC CAG GCG ACC GAT ATC GAA ATT CAT GCC CGT GAA ATT CTG AAA GTT AAA GGG CGC ATG AAT GAA CTT ATG GCG CTT CAT ACG GGT CAA TCA TTA GAA CAG ATT GAA CGT GAT ACC GAG CGC GAT CGC TTC CTT TCC GCC CCT GAA GCG GTG GAA TAC GGT CTG GTC GAT TCG ATT CTG ACC CAT CGT AAT TGA");
		map.put("clpS", "ATG GGT AAA ACG AAC GAC TGG CTG GAC TTT GAT CAA CTG GCG GAA GAA AAA GTT CGC GAC GCG CTA AAA CCG CCA TCT ATG TAT AAA GTG ATA TTA GTC AAT GAT GAT TAC ACT CCG ATG GAG TTT GTT ATT GAC GTG TTA CAA AAA TTC TTT TCT TAT GAT GTA GAA CGT GCA ACG CAA TTG ATG CTC GCT GTT CAC TAC CAG GGG AAG GCC ATT TGC GGA GTC TTT ACC GCC GAG GTT GCA GAA ACC AAA GTG GCG ATG GTG AAC AAG TAC GCG AGG GAG AAT GAG CAT CCA TTG CTG TGT ACG CTA GAA AAA GCC TGA");
		map.put("clpX", "ATG ACA GAT AAA CGC AAA GAT GGC TCA GGC AAA TTG CTG TAT TGC TCT TTT TGC GGC AAA AGC CAG CAT GAA GTG CGC AAG CTG ATT GCC GGT CCA TCC GTG TAT ATC TGC GAC GAA TGT GTT GAT TTA TGT AAC GAC ATC ATT CGC GAA GAG ATT AAA GAA GTT GCA CCG CAT CGT GAA CGC AGT GCG CTA CCG ACG CCG CAT GAA ATT CGC AAC CAC CTG GAC GAT TAC GTT ATC GGC CAG GAA CAG GCG AAA AAA GTG CTG GCG GTC GCG GTA TAC AAC CAT TAC AAA CGT CTG CGC AAC GGC GAT ACC AGC AAT GGC GTC GAG TTG GGC AAA AGT AAC ATT CTG CTG ATC GGT CCG ACC GGT TCC GGT AAA ACG CTG CTG GCT GAA ACG CTG GCG CGC CTG CTG GAT GTT CCG TTC ACC ATG GCC GAC GCG ACT ACA CTG ACC GAA GCC GGT TAT GTG GGT GAA GAC GTT GAA AAC ATC ATT CAG AAG CTG TTG CAG AAA TGC GAC TAC GAT GTC CAG AAA GCA CAG CGT GGT ATT GTC TAC ATC GAT GAA ATC GAC AAG ATT TCT CGT AAG TCA GAC AAC CCG TCC ATT ACC CGA GAC GTT TCC GGT GAA GGC GTA CAG CAG GCA CTG TTG AAA CTG ATC GAA GGT ACG GTA GCT GCT GTT CCA CCG CAA GGT GGG CGT AAA CAT CCG CAG CAG GAA TTC TTG CAG GTT GAT ACC TCT AAG ATC CTG TTT ATT TGT GGC GGT GCG TTT GCC GGT CTG GAT AAA GTG ATT TCC CAC CGT GTA GAA ACC GGC TCC GGC ATT GGT TTT GGC GCG ACG GTA AAA GCG AAG TCC GAC AAA GCA AGC GAA GGC GAG CTG CTG GCG CAG GTT GAA CCG GAA GAT CTG ATC AAG TTT GGT CTT ATC CCT GAG TTT ATT GGT CGT CTG CCG GTT GTC GCA ACG TTG AAT GAA CTG AGC GAA GAA GCT CTG ATT CAG ATC CTC AAA GAG CCG AAA AAC GCC CTG ACC AAG CAG TAT CAG GCG CTG TTT AAT CTG GAA GGC GTG GAT CTG GAA TTC CGT GAC GAG GCG CTG GAT GCT ATC GCT AAG AAA GCG ATG GCG CGT AAA ACC GGT GCC CGT GGC CTG CGT TCC ATC GTA GAA GCC GCA CTG CTC GAT ACC ATG TAC GAT CTG CCG TCC ATG GAA GAC GTC GAA AAA GTG GTT ATC GAC GAG TCG GTA ATT GAT GGT CAA AGC AAA CCG TTG CTG ATT TAT GGC AAG CCG GAA GCG CAA CAG GCA TCT GGT GAA TAA");
		map.put("dnaJ", "ATG GCT AAG CAA GAT TAT TAC GAG ATT TTA GGC GTT TCC AAA ACA GCG GAA GAG CGT GAA ATC AGA AAG GCC TAC AAA CGC CTG GCC ATG AAA TAC CAC CCG GAC CGT AAC CAG GGT GAC AAA GAG GCC GAG GCG AAA TTT AAA GAG ATC AAG GAA GCT TAT GAA GTT CTG ACC GAC TCG CAA AAA CGT GCG GCA TAC GAT CAG TAT GGT CAT GCT GCG TTT GAG CAA GGT GGC ATG GGC GGC GGC GGT TTT GGC GGC GGC GCA GAC TTC AGC GAT ATT TTT GGT GAC GTT TTC GGC GAT ATT TTT GGC GGC GGA CGT GGT CGT CAA CGT GCG GCG CGC GGT GCT GAT TTA CGC TAT AAC ATG GAG CTC ACC CTC GAA GAA GCT GTA CGT GGC GTG ACC AAA GAG ATC CGC ATT CCG ACT CTG GAA GAG TGT GAC GTT TGC CAC GGT AGC GGT GCA AAA CCA GGT ACA CAG CCG CAG ACT TGT CCG ACC TGT CAT GGT TCT GGT CAG GTG CAG ATG CGC CAG GGA TTC TTC GCT GTA CAG CAG ACC TGT CCA CAC TGT CAG GGC CGC GGT ACG CTG ATC AAA GAT CCG TGC AAC AAA TGT CAT GGT CAT GGT CGT GTT GAG CGC AGC AAA ACG CTG TCC GTT AAA ATC CCG GCA GGG GTG GAC ACT GGA GAC CGC ATC CGT CTT GCG GGC GAA GGT GAA GCG GGC GAG CAT GGC GCA CCG GCA GGC GAT CTG TAC GTT CAG GTT CAG GTT AAA CAG CAC CCG ATT TTC GAG CGT GAA GGC AAC AAC CTG TAT TGC GAA GTC CCG ATC AAC TTC GCT ATG GCG GCG CTG GGT GGC GAA ATC GAA GTA CCG ACC CTT GAT GGT CGC GTC AAA CTG AAA GTG CCT GGC GAA ACC CAG ACC GGT AAG CTA TTC CGT ATG CGC GGT AAA GGC GTC AAG TCT GTC CGC GGT GGC GCA CAG GGT GAT TTG CTG TGC CGC GTT GTC GTC GAA ACA CCG GTA GGC CTG AAC GAA AGG CAG AAA CAG CTG CTG CAA GAG CTG CAA GAA AGC TTC GGT GGC CCA ACC GGC GAG CAC AAC AGC CCG CGC TCA AAG AGC TTC TTT GAT GGT GTG AAG AAG TTT TTT GAC GAC CTG ACC CGC TAA");
		map.put("dnaK", "ATG GGT AAA ATA ATT GGT ATC GAC CTG GGT ACT ACC AAC TCT TGT GTA GCG ATT ATG GAT GGC ACC ACT CCT CGC GTG CTG GAG AAC GCC GAA GGC GAT CGC ACC ACG CCT TCT ATC ATT GCC TAT ACC CAG GAT GGT GAA ACT CTA GTT GGT CAG CCG GCT AAA CGT CAG GCA GTG ACG AAC CCG CAA AAC ACT CTG TTT GCG ATT AAA CGC CTG ATT GGT CGC CGC TTC CAG GAC GAA GAA GTA CAG CGT GAT GTT TCC ATC ATG CCG TTC AAA ATT ATT GCT GCT GAT AAC GGC GAC GCA TGG GTC GAA GTT AAA GGC CAG AAA ATG GCA CCG CCG CAG ATT TCT GCT GAA GTG CTG AAA AAA ATG AAG AAA ACC GCT GAA GAT TAC CTG GGT GAA CCG GTA ACT GAA GCT GTT ATC ACC GTA CCG GCA TAC TTT AAC GAT GCT CAG CGT CAG GCA ACC AAA GAC GCA GGC CGT ATC GCT GGT CTG GAA GTA AAA CGT ATC ATC AAC GAA CCG ACC GCA GCT GCG CTG GCT TAC GGT CTG GAC AAA GGC ACT GGC AAC CGT ACT ATC GCG GTT TAT GAC CTG GGT GGT GGT ACT TTC GAT ATT TCT ATT ATC GAA ATC GAC GAA GTT GAC GGC GAA AAA ACC TTC GAA GTT CTG GCA ACC AAC GGT GAT ACC CAC CTG GGG GGT GAA GAC TTC GAC AGC CGT CTG ATC AAC TAT CTG GTT GAA GAA TTC AAG AAA GAT CAG GGC ATT GAC CTG CGC AAC GAT CCG CTG GCA ATG CAG CGC CTG AAA GAA GCG GCA GAA AAA GCG AAA ATC GAA CTG TCT TCC GCT CAG CAG ACC GAC GTT AAC CTG CCA TAC ATC ACT GCA GAC GCG ACC GGT CCG AAA CAC ATG AAC ATC AAA GTG ACT CGT GCG AAA CTG GAA AGC CTG GTT GAA GAT CTG GTA AAC CGT TCC ATT GAG CCG CTG AAA GTT GCA CTG CAG GAC GCT GGC CTG TCC GTA TCT GAT ATC GAC GAC GTT ATC CTC GTT GGT GGT CAG ACT CGT ATG CCA ATG GTT CAG AAG AAA GTT GCT GAG TTC TTT GGT AAA GAG CCG CGT AAA GAC GTT AAC CCG GAC GAA GCT GTA GCA ATC GGT GCT GCT GTT CAG GGT GGT GTT CTG ACT GGT GAC GTA AAA GAC GTA CTG CTG CTG GAC GTT ACC CCG CTG TCT CTG GGT ATC GAA ACC ATG GGC GGT GTG ATG ACG ACG CTG ATC GCG AAA AAC ACC ACT ATC CCG ACC AAG CAC AGC CAG GTG TTC TCT ACC GCT GAA GAC AAC CAG TCT GCG GTA ACC ATC CAT GTG CTG CAG GGT GAA CGT AAA CGT GCG GCT GAT AAC AAA TCT CTG GGT CAG TTC AAC CTA GAT GGT ATC AAC CCG GCA CCG CGC GGC ATG CCG CAG ATC GAA GTT ACC TTC GAT ATC GAT GCT GAC GGT ATC CTG CAC GTT TCC GCG AAA GAT AAA AAC AGC GGT AAA GAG CAG AAG ATC ACC ATC AAG GCT TCT TCT GGT CTG AAC GAA GAT GAA ATC CAG AAA ATG GTA CGC GAC GCA GAA GCT AAC GCC GAA GCT GAC CGT AAG TTT GAA GAG CTG GTA CAG ACT CGC AAC CAG GGC GAC CAT CTG CTG CAC AGC ACC CGT AAG CAG GTT GAA GAA GCA GGC GAC AAA CTG CCG GCT GAC GAC AAA ACT GCT ATC GAG TCT GCG CTG ACT GCA CTG GAA ACT GCT CTG AAA GGT GAA GAC AAA GCC GCT ATC GAA GCG AAA ATG CAG GAA CTG GCA CAG GTT TCC CAG AAA CTG ATG GAA ATC GCC CAG CAG CAA CAT GCC CAG CAG CAG ACT GCC GGT GCT GAT GCT TCT GCA AAC AAC GCG AAA GAT GAC GAT GTT GTC GAC GCT GAA TTT GAA GAA GTC AAA GAC AAA AAA TAA");
		map.put("ftsH", "ATG GCG AAA AAC CTA ATA CTC TGG CTG GTC ATT GCC GTT GTG CTG ATG TCA GTA TTC CAG AGC TTT GGG CCC AGC GAG TCT AAT GGC CGT AAG GTG GAT TAC TCT ACC TTC CTA CAA GAG GTC AAT AAC GAC CAG GTT CGT GAA GCG CGT ATC AAC GGA CGT GAA ATC AAC GTT ACC AAG AAA GAT AGT AAC CGT TAT ACC ACT TAC ATT CCG GTT CAG GAT CCG AAA TTA CTG GAT AAC CTG TTG ACC AAG AAC GTC AAG GTT GTC GGT GAA CCG CCT GAA GAA CCA AGC CTG CTG GCT TCT ATC TTC ATC TCC TGG TTC CCG ATG CTG TTG CTG ATT GGT GTC TGG ATC TTC TTC ATG CGT CAA ATG CAG GGC GGC GGT GGC AAA GGT GCC ATG TCG TTT GGT AAG AGC AAA GCG CGC ATG CTG ACG GAA GAT CAG ATC AAA ACG ACC TTT GCT GAC GTT GCG GGC TGC GAC GAA GCA AAA GAA GAA GTT GCT GAA CTG GTT GAG TAT CTG CGC GAG CCG AGC CGC TTC CAG AAA CTC GGC GGT AAG ATC CCG AAA GGC GTC TTG ATG GTC GGT CCT CCG GGT ACC GGT AAA ACG CTG CTG GCG AAA GCG ATT GCA GGC GAA GCG AAA GTT CCG TTC TTT ACT ATC TCC GGT TCT GAC TTC GTA GAA ATG TTC GTC GGT GTG GGT GCA TCC CGT GTT CGT GAC ATG TTC GAA CAG GCG AAG AAA GCG GCA CCG TGC ATC ATC TTT ATC GAT GAA ATC GAC GCC GTA GGC CGC CAG CGT GGC GCT GGT CTG GGC GGT GGT CAC GAT GAA CGT GAA CAG ACT CTG AAC CAG ATG CTG GTT GAG ATG GAT GGC TTC GAA GGT AAC GAA GGT ATC ATC GTT ATC GCC GCG ACT AAC CGT CCG GAC GTT CTC GAC CCG GCC CTG CTG CGT CCT GGC CGT TTC GAC CGT CAG GTT GTG GTC GGC TTG CCA GAT GTT CGC GGT CGT GAG CAG ATC CTG AAA GTT CAC ATG CGT CGC GTA CCA TTG GCA CCC GAT ATC GAC GCG GCA ATC ATT GCC CGT GGT ACT CCT GGT TTC TCC GGT GCT GAC CTG GCG AAC CTG GTG AAC GAA GCG GCA CTG TTC GCT GCT CGT GGC AAC AAA CGC GTT GTG TCG ATG GTT GAG TTC GAG AAA GCG AAA GAC AAA ATC ATG ATG GGT GCG GAA CGT CGC TCC ATG GTG ATG ACG GAA GCG CAG AAA GAA TCG ACG GCT TAC CAC GAA GCG GGT CAT GCG ATT ATC GGT CGC CTG GTG CCG GAA CAC GAT CCG GTG CAC AAA GTG ACG ATT ATC CCA CGC GGT CGT GCG CTG GGT GTG ACT TTC TTC TTG CCT GAG GGC GAC GCA ATC AGC GCC AGC CGT CAG AAA CTG GAA AGC CAG ATT TCT ACG CTG TAC GGT GGT CGT CTG GCA GAA GAG ATC ATC TAC GGG CCG GAA CAT GTA TCT ACC GGT GCG TCC AAC GAT ATT AAA GTT GCG ACC AAC CTG GCA CGT AAC ATG GTG ACT CAG TGG GGC TTC TCT GAG AAA TTG GGT CCA CTG CTG TAC GCG GAA GAA GAA GGT GAA GTG TTC CTC GGC CGT AGC GTA GCG AAA GCG AAA CAT ATG TCC GAT GAA ACT GCA CGT ATC ATC GAC CAG GAA GTG AAA GCA CTG ATT GAG CGT AAC TAT AAT CGT GCG CGT CAG CTT CTG ACC GAC AAT ATG GAT ATT CTG CAT GCG ATG AAA GAT GCT CTC ATG AAA TAT GAG ACT ATC GAC GCA CCG CAG ATT GAT GAC CTG ATG GCA CGT CGC GAT GTA CGT CCG CCA GCG GGC TGG GAA GAA CCA GGC GCT TCT AAC AAT TCT GGC GAC AAT GGT AGT CCA AAG GCT CCT CGT CCG GTT GAT GAA CCG CGT ACG CCG AAC CCG GGT AAC ACC ATG TCA GAG CAG TTA GGC GAC AAG TAA");
		//map.put("hslU", "ATG TCT GAA ATG ACC CCA CGC GAA ATC GTC AGC GAA CTG GAT AAG CAC ATC ATC GGC CAG GAC AAC GCC AAG CGT TCT GTG GCG ATT GCT CTG CGT AAC CGC TGG CGT CGC ATG CAG CTC AAC GAA GAG CTG CGC CAT GAA GTG ACC CCG AAA AAT ATC CTG ATG ATC GGC CCG ACC GGT GTC GGT AAA ACT GAA ATC GCC CGT CGT CTG GCT AAG CTG GCG AAT GCG CCG TTC ATC AAA GTT GAA GCG ACC AAA TTC ACC GAA GTG GGC TAC GTC GGT AAG GAA GTG GAT TCT ATT ATT CGC GAT CTG ACC GAT GCC GCC GTG AAA ATG GTA CGC GTC CAG GCT ATC GAG AAA AAC CGT TAT CGC GCT GAA GAA CTG GCA GAA GAA CGT ATT CTC GAC GTG CTG ATC CCA CCT GCT AAA AAC AAC TGG GGA CAG ACC GAA CAG CAG CAG GAA CCG TCC GCT GCT CGT CAG GCA TTC CGC AAA AAA CTG CGT GAA GGC CAG CTT GAT GAC AAA GAA ATC GAG ATC GAT CTT GCC GCA GCA CCG ATG GGC GTT GAA ATT ATG GCT CCT CCG GGC ATG GAA GAG ATG ACC AGC CAG CTG CAG TCC ATG TTC CAG AAC CTG GGC GGC CAG AAG CAA AAA GCG CGT AAG CTG AAA ATC AAA GAC GCC ATG AAG CTG CTG ATT GAA GAA GAA GCG GCG AAA CTG GTG AAC CCG GAA GAG CTG AAG CAA GAC GCT ATC GAC GCT GTT GAG CAG CAC GGG ATC GTG TTT ATC GAC GAA ATC GAC AAA ATC TGT AAG CGC GGC GAG TCT TCC GGT CCG GAT GTT TCT CGT GAA GGC GTT CAG CGT GAC CTG CTG CCG CTG GTA GAA GGT TGC ACC GTT TCC ACC AAA CAC GGG ATG GTC AAA ACT GAC CAC ATT CTG TTT ATC GCT TCT GGC GCG TTC CAG ATT GCG AAA CCG TCT GAC CTG ATC CCG GAA CTG CAA GGT CGT CTG CCA ATC CGC GTT GAA CTG CAG GCG CTG ACC ACC AGC GAC TTC GAG CGT ATT CTG ACC GAG CCG AAT GCC TCT ATC ACC GTG CAG TAC AAA GCA CTG ATG GCG ACT GAA GGC GTA AAT ATC GAG TTT ACC GAC TCC GGT ATT AAA CGC ATC GCG GAA GCG GCA TGG CAG GTG AAC GAA TCT ACC GAA AAC ATC GGT GCT CGT CGT TTA CAC ACT GTT CTG GAG CGT TTA ATG GAA GAG ATT TCC TAC GAC GCC AGC GAT TTA AGC GGT CAA AAT ATC ACT ATT GAC GCA GAT TAT GTG AGC AAA CAT CTG GAT GCG TTG GTG GCA GAT GAA GAT CTG AGC CGT TTT ATC CTA TAA");
		//map.put("hslV", "GTG ACA ACT ATA GTA AGC GTA CGC CGT AAC GGC CAT GTG GTC ATC GCT GGT GAT GGT CAG GCC ACG TTG GGC AAT ACC GTA ATG AAA GGC AAC GTG AAA AAG GTC CGC CGT CTG TAC AAC GAC AAA GTC ATC GCG GGC TTT GCG GGC GGT ACT GCG GAT GCT TTT ACG CTG TTC GAA CTG TTT GAA CGT AAA CTG GAA ATG CAT CAG GGC CAT CTG GTC AAA GCC GCC GTT GAG CTG GCA AAA GAC TGG CGT ACC GAT CGC ATG CTG CGC AAA CTT GAA GCA CTG CTG GCA GTC GCG GAT GAA ACT GCA TCG CTT ATC ATC ACC GGT AAC GGT GAC GTG GTG CAG CCA GAA AAC GAT CTT ATT GCT ATC GGC TCC GGC GGC CCT TAC GCC CAG GCT GCG GCG CGC GCG CTG TTA GAA AAC ACT GAA CTT AGC GCC CGT GAA ATT GCT GAA AAG GCG TTG GAT ATT GCA GGC GAC ATT TGC ATC TAT ACC AAC CAT TTC CAC ACC ATC GAA GAA TTA AGC TAC AAA GCG TAA");
		map.put("lon",  "ATG AAT CCT GAG CGT TCT GAA CGC ATT GAA ATC CCC GTA TTG CCG CTG CGC GAT GTG GTG GTT TAT CCG CAC ATG GTC ATC CCC TTA TTT GTC GGG CGG GAA AAA TCT ATC CGT TGT CTG GAA GCG GCG ATG GAC CAT GAT AAA AAA ATT ATG CTG GTC GCG CAG AAA GAA GCT TCA ACG GAT GAG CCG GGT GTA AAC GAT CTT TTC ACC GTC GGG ACC GTG GCC TCT ATA TTG CAG ATG CTG AAA CTG CCT GAC GGC ACC GTC AAA GTG CTG GTC GAG GGG TTA CAG CGC GCG CGT ATT TCT GCG CTC TCT GAC AAT GGC GAA CAC TTT TCT GCG AAG GCG GAG TAT CTG GAG TCG CCG ACC ATT GAT GAG CGG GAA CAG GAA GTG CTG GTG CGT ACT GCA ATC AGC CAG TTC GAA GGC TAC ATC AAG CTG AAC AAA AAA ATC CCA CCA GAA GTG CTG ACG TCG CTG AAT AGC ATC GAC GAT CCG GCG CGT CTG GCG GAT ACC ATT GCT GCA CAT ATG CCG CTG AAA CTG GCT GAC AAA CAG TCT GTT CTG GAG ATG TCC GAC GTT AAC GAA CGT CTG GAA TAT CTG ATG GCA ATG ATG GAA TCG GAA ATC GAT CTG CTG CAG GTT GAG AAA CGC ATT CGC AAC CGC GTT AAA AAG CAG ATG GAG AAA TCC CAG CGT GAG TAC TAT CTG AAC GAG CAA ATG AAA GCT ATT CAG AAA GAA CTC GGT GAA ATG GAC GAC GCG CCG GAC GAA AAC GAA GCC CTG AAG CGC AAA ATC GAC GCG GCG AAG ATG CCG AAA GAG GCA AAA GAG AAA GCG GAA GCA GAG TTG CAG AAG CTG AAA ATG ATG TCT CCG ATG TCG GCA GAA GCG ACC GTA GTG CGT GGT TAT ATC GAC TGG ATG GTA CAG GTG CCG TGG AAT GCG CGT AGC AAG GTC AAA AAA GAC CTG CGT CAG GCG CAG GAA ATC CTT GAT ACC GAC CAT TAT GGT CTG GAG CGC GTG AAA GAT CGA ATC CTT GAG TAT CTT GCG GTT CAA AGC CGT GTC AAC AAA ATC AAG GGA CCG ATC CTC TGC CTG GTA GGG CCG CCG GGG GTA GGT AAA ACC TCT CTT GGT CAG TCC ATT GCC AAA GCC ACC GGG CGT AAA TAT GTC CGT ATG GCG CTG GGC GGC GTG CGT GAT GAA GCG GAA ATC CGT GGT CAC CGC CGT ACT TAC ATC GGT TCT ATG CCG GGT AAA CTG ATC CAG AAA ATG GCG AAA GTG GGC GTG AAA AAC CCG CTG TTC CTG CTC GAT GAG ATC GAC AAA ATG TCT TCT GAC ATG CGT GGC GAT CCG GCC TCT GCA CTG CTT GAA GTG CTG GAT CCA GAG CAG AAC GTA GCG TTC AGC GAC CAC TAC CTG GAA GTG GAT TAC GAT CTC AGC GAC GTG ATG TTT GTC GCG ACG TCG AAC TCC ATG AAC ATT CCG GCA CCG CTG CTG GAT CGT ATG GAA GTG ATT CGC CTC TCC GGT TAT ACC GAA GAT GAA AAA CTG AAC ATC GCC AAA CGT CAC CTG CTG CCG AAG CAG ATT GAA CGT AAT GCA CTG AAA AAA GGT GAG CTG ACC GTC GAC GAT AGC GCC ATT ATC GGC ATT ATT CGT TAC TAC ACC CGT GAG GCG GGC GTG CGT GGT CTG GAG CGT GAA ATC TCC AAA CTG TGT CGC AAA GCG GTT AAG CAG TTA CTG CTC GAT AAG TCA TTA AAA CAT ATC GAA ATT AAC GGC GAT AAC CTG CAT GAC TAT CTC GGT GTT CAG CGT TTC GAC TAT GGT CGC GCG GAT AAC GAA AAC CGT GTC GGT CAG GTA ACC GGT CTG GCG TGG ACG GAA GTG GGC GGT GAC TTG CTG ACC ATT GAA ACC GCA TGT GTT CCG GGT AAA GGC AAA CTG ACC TAT ACC GGT TCG CTC GGC GAA GTG ATG CAG GAG TCC ATT CAG GCG GCG TTA ACG GTG GTT CGT GCG CGT GCG GAA AAA CTG GGG ATC AAC CCT GAT TTT TAC GAA AAA CGT GAC ATC CAC GTC CAC GTA CCG GAA GGT GCG ACG CCG AAA GAT GGT CCG AGT GCC GGT ATT GCT ATG TGC ACC GCG CTG GTT TCT TGC CTG ACC GGT AAC CCG GTT CGT GCC GAT GTG GCA ATG ACC GGT GAG ATC ACT CTG CGT GGT CAG GTA CTG CCG ATC GGT GGT TTG AAA GAA AAA CTC CTG GCA GCG CAT CGC GGC GGG ATT AAA ACA GTG CTA ATT CCG TTC GAA AAT AAA CGC GAT CTG GAA GAG ATT CCT GAC AAC GTA ATT GCC GAT CTG GAC ATT CAT CCT GTG AAG CGC ATT GAG GAA GTT CTG ACT CTG GCG CTG CAA AAT GAA CCG TCT GGT ATG CAG GTT GTG ACT GCA AAA TAG");
		
		//TODO Implement reverse complement method for hslU and hslV
		
		//regex
		String regexPattern = "(((((([ACGT]{0,2}?)(C[ACGT\\s]{1,5}?))([ACG](AA|AG|GA)\\s))|((([ACGT]{0,2}?)(C[ACGT\\s]{1,3}?))(T(C[AG]|GG|T[AG])\\s))|((([ACGT]{1,2}?)(C[ACGT\\s]{1,2}?))(T(A[GT]|G[CGT])\\s)))(([ACGT]{3}\\s){0,3}?)){2})";
		/*
		 * REGEX EXPLANATION
		 *                           [ACGT]{0,2}?  C[ACGT\\s]{1,5}?                           [ACGT]{0,2}?  C[ACGT\\s]{1,3}?                             [ACGT]{1,2}?  C[ACGT\\s]{1,2}?                                                         Step 1/4: Finding CXX, XCX, or XXC
		 *                                                            [ACG](AA|AG|GA)\\s                                       T(C[AG]|GG|T[AG])\\s                                       T(A[GT]|G[CGT])\\s                                    Step 2/5: Finding codons that are one different from a stop codon (TAA/TAG/TGA)
		 *                                                                                                                                                                                                      ([ACGT]{3}\\s){0,3}?            Step 3:   Separating steps 1 and 2 from steps 4 and 5 by up to 3 codons
		 */
		
		String desiredGene = keyboard.nextLine();
		System.out.println(desiredGene);
		String geneSequence = map.get(desiredGene);
		System.out.println("Length: " + geneSequence.replaceAll(" ", "").length() + "\n");
		
		//String geneSequence35 = reverseComplement(geneSequence53);
		
		Pattern regex = Pattern.compile(regexPattern);
		Matcher matcher = regex.matcher(geneSequence);
		
		List<String> matches = new ArrayList<>();
		List<Integer> matchedIndexes = new ArrayList<>();
		
		while (matcher.find()) {
			matches.add(matcher.group());
			matchedIndexes.add(matcher.start());				
		}
		
		for (int i = 0; i < matches.size(); i++) {
			String match = matches.get(i);
			int matchIndex = matchedIndexes.get(i) * 3 / 4 + 1;
			
			System.out.println("Matched \"" + match + "\" at base pair " + matchIndex);
			
			final int OLIGO_OFFSET = 80;
			int startIndex = Math.max(0, matchedIndexes.get(i) - OLIGO_OFFSET);
			int endIndex = Math.min(matchedIndexes.get(i) + match.length() + OLIGO_OFFSET, geneSequence.length());
			String oligo = reverseComplement(geneSequence.substring(startIndex, endIndex));
			
			int printStartIndex = startIndex * 3 / 4 + 1;
			int printEndIndex = endIndex * 3 / 4;
			
			String endIndexSpace = "";
			if(endIndex != startIndex + match.length() + 2 * OLIGO_OFFSET && printStartIndex != 1) {
				endIndexSpace = " ";
				printEndIndex++;
			}
			
			System.out.println("Possible oligo inside: (" + printEndIndex + ")" + endIndexSpace + oligo + " (" + printStartIndex + ")");
			
			String oligoNoSpaces = oligo.replace(" ", "").toLowerCase();
			if(oligoNoSpaces.length() > 120) {
				int oligoNoSpacesLength = oligoNoSpaces.length();
				String oligoRegexHighlight = oligoNoSpaces.substring(0, 60) + oligoNoSpaces.toUpperCase().substring(60, oligoNoSpacesLength - 60) + oligoNoSpaces.substring(oligoNoSpacesLength - 60, oligoNoSpacesLength);
				System.out.println("Oligo with highlight: " + oligoRegexHighlight);
				String oligoReverseComplement = reverseComplement(oligoRegexHighlight);
				System.out.println("Oligo reverse complement: " + oligoReverseComplement);
			}
			System.out.print("\n");
		}
		
		String casedGene = "";
		int endOfLastMatch = 0;
		for (int i = 0; i < matches.size(); i++) {
			String match = matches.get(i);
			int matchIndex = matchedIndexes.get(i);
			
			if (matchIndex > endOfLastMatch) {
				casedGene += geneSequence.substring(endOfLastMatch, matchIndex).toLowerCase();
			}
			casedGene += match.toUpperCase();
			endOfLastMatch = matchIndex + match.length();
		}

		if (endOfLastMatch != geneSequence.length() - 1) {
			casedGene += geneSequence.substring(endOfLastMatch, geneSequence.length()).toLowerCase();
		}
		
		System.out.println("Gene: " + "5'  " + casedGene + "  3'");
	}
	
	public static String reverseComplement(String dnaSequence) {
		String complement = "";
		char[] dnaSequenceCharacters = dnaSequence.toCharArray();
		for(int i = 0; i < dnaSequence.length(); i++) {
			char character = dnaSequenceCharacters[i];
			
			if(character == 'A') {
				complement += 'T';
			}
			else if(character == 'C') {
				complement += 'G';
			}
			else if(character == 'G') {
				complement += 'C';
			}
			else if(character == 'T') {
				complement += 'A';
			}
			else if(character == ' ') {
				complement += ' ';
			}
			else if(character == 'a') {
				complement += 't';
			}
			else if(character == 'g') {
				complement += 'c';
			}
			else if(character == 'c') {
				complement += 'g';
			}
			else if(character == 't') {
				complement += 'a';
			}
		}
		
		String reverseComplement = "";
		
		for(int i = complement.length() - 1; i >= 0; i--) {
			reverseComplement += complement.charAt(i);
		}
		
		return reverseComplement;
	}
}
