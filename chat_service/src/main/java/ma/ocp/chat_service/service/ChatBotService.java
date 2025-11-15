package ma.ocp.chat_service.service;


import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ChatBotService {

    private final GeminiService geminiService;
    private final SolutionClientService solutionClientService;

    public ChatBotService(GeminiService geminiService, SolutionClientService solutionClientService) {
        this.geminiService = geminiService;
        this.solutionClientService = solutionClientService;
    }

    public String buildPrompt(String question) {
    	
    	List<String> anciennesSolutions = solutionClientService.getAnciennesSolutions(question);
    	System.out.println("Anciennes solutions : " + anciennesSolutions);

        StringBuilder solutions = new StringBuilder();
        for (String sol : anciennesSolutions) {
            solutions.append("- ").append(sol).append("\n");
        }

    	
        String contexteApp = """
        Je suis un assistant virtuel pour l'application de gestion des interventions du groupe OCP.

        Voici le fonctionnement de l'application :

        1. Administrateurs :
           - Gèrent les comptes utilisateurs : ajouter, supprimer, activer/désactiver.
           - Gèrent les équipements : ajouter, modifier, supprimer, affecter à un utilisateur, consulter l’historique des interventions.
           - Consultent la liste des interventions avec tous les détails.
           - Accèdent aux statistiques des utilisateurs et des interventions via Power BI.

        2. Responsables :
           - Reçoivent les demandes des utilisateurs et peuvent les accepter ou les rejeter.
           - Pour les demandes acceptées, ils créent des interventions et les affectent aux techniciens selon le niveau requis.

        3. Techniciens :
           - Trois niveaux disponibles.
           - Peuvent voir leurs interventions planifiées, effectuer l’intervention, remplir le diagnostic et la solution.
           - Si un technicien ne peut pas réaliser une intervention, il peut l’escalader, et le responsable affecte un technicien de niveau supérieur.

        4. Utilisateurs / Demandeurs :
           - Créent des demandes et suivent leur progression.
           - Valident la solution réalisée par le technicien.

        5. Exemple pour créer une demande :
           - L’utilisateur doit se connecter.
           - Aller dans le menu "Demandes" et cliquer sur "Nouvelle Demande".
           - Remplir le formulaire avec : titre, lieu, équipement, niveau, priorité, description.
           - Cliquer sur "Soumettre la demande".
         Réponds uniquement à la question posée de manière concise, claire et pratique.
        """;

        return contexteApp +
                "\nAnciennes solutions connues :\n" + solutions +
               "\nQuestion de l’utilisateur : " + question +
               "\nRéponds de manière claire et détaillée, propose des étapes ou solutions concrètes.";
    }

    public String ask(String question) {
        String prompt = buildPrompt(question);
        return geminiService.askGemini(prompt);
    }
}
