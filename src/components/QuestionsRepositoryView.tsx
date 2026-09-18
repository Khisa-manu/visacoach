import React, { useState } from 'react';
import { HelpCircle, Tag, Search, CheckCircle } from 'lucide-react';

interface QuestionSeed {
  category: string;
  categoryLabel: string;
  questions: string[];
}

const SEED_QUESTIONS: QuestionSeed[] = [
  {
    category: "TRAVEL_PURPOSE",
    categoryLabel: "1. Purpose of Travel",
    questions: [
      "What is the specific purpose of your trip to the United States?",
      "Why are you traveling at this particular time of year?",
      "What specific cities or tourist destinations do you intend to visit?",
      "How long do you plan to remain in the United States?",
      "Have you already purchased your flight tickets or made hotel bookings?",
      "Are you attending any specific event, conference, or personal celebration?"
    ]
  },
  {
    category: "EMPLOYMENT",
    categoryLabel: "2. Employment & Occupation",
    questions: [
      "What is your current occupation and who is your employer in Kenya?",
      "How long have you been employed with your current organization?",
      "What are your core day-to-day duties and responsibilities?",
      "Has your employer approved your annual leave for this travel window?",
      "If self-employed, what is the nature of your business and how long has it operated?",
      "Who will manage your professional duties while you are away?"
    ]
  },
  {
    category: "FINANCIAL_ABILITY",
    categoryLabel: "3. Financial Ability & Funding",
    questions: [
      "Who will be paying for the expenses of your trip?",
      "What is your estimated total budget for this visit in USD or KES?",
      "What is your monthly or annual income from your employment or business?",
      "Can you explain the primary source of funds in your bank account?",
      "If a sponsor is assisting with costs, what is your relationship to them?"
    ]
  },
  {
    category: "ACCOMMODATION",
    categoryLabel: "4. Accommodation & Itinerary",
    questions: [
      "Where will you be staying during your stay in the United States?",
      "Do you have confirmed reservations at a hotel, Airbnb, or private residence?",
      "What activities or itinerary do you have planned during your visit?",
      "How will you be traveling between different cities during your stay?"
    ]
  },
  {
    category: "TIES_TO_HOME_COUNTRY",
    categoryLabel: "5. Ties to Home Country (Kenya)",
    questions: [
      "What family members will remain in Kenya while you travel?",
      "Do you own real estate, property, land, or significant assets in Kenya?",
      "What strong commitments guarantee that you will return to Kenya after your visit?",
      "What ongoing career obligations require your physical return?"
    ]
  },
  {
    category: "TRAVEL_HISTORY",
    categoryLabel: "6. International Travel History",
    questions: [
      "Have you previously traveled outside of Kenya?",
      "Which countries have you visited over the past five years and for what purpose?",
      "Did you return to Kenya within the authorized stay period on your previous travels?",
      "Have you ever traveled to the United States before?"
    ]
  },
  {
    category: "US_CONNECTIONS",
    categoryLabel: "7. Family & Contacts in the U.S.",
    questions: [
      "Do you have any immediate or extended relatives residing in the United States?",
      "What is the immigration status of your relatives in the U.S. (citizen, LPR, visa)?",
      "Do you have personal friends or professional associates in the United States?",
      "Will you be staying with any relatives or friends during your trip?"
    ]
  },
  {
    category: "RETURN_INTENT",
    categoryLabel: "8. Return Intent & Timeline",
    questions: [
      "On what exact date do you intend to return to Kenya?",
      "When are you expected to resume work at your job in Nairobi?",
      "Why would you not remain in the United States past your authorized stay?"
    ]
  },
  {
    category: "MARITAL_STATUS",
    categoryLabel: "9. Marital Status & Family Ties",
    questions: [
      "Are you married, and does your spouse reside with you in Kenya?",
      "Do you have children in Kenya, and what are their ages?",
      "Will your spouse or children be accompanying you on this trip or staying in Kenya?"
    ]
  },
  {
    category: "SPECIAL_CIRCUMSTANCES",
    categoryLabel: "10. Special Inquiries & Follow-ups",
    questions: [
      "Have you ever had a previous U.S. visa refusal under INA Section 214(b)?",
      "If previously refused, what circumstances in your life have changed since your last application?",
      "Has anyone ever filed an immigrant petition on your behalf?"
    ]
  }
];

export const QuestionsRepositoryView: React.FC = () => {
  const [selectedCategory, setSelectedCategory] = useState<string>("ALL");
  const [searchQuery, setSearchQuery] = useState("");

  const filteredCategories = SEED_QUESTIONS.filter(cat => {
    if (selectedCategory !== "ALL" && cat.category !== selectedCategory) return false;
    return true;
  });

  return (
    <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-sm">
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 pb-5 border-b border-slate-100">
        <div>
          <div className="flex items-center gap-2">
            <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-semibold bg-indigo-50 text-indigo-700 border border-indigo-200">
              Database Seed V3
            </span>
            <span className="text-xs text-slate-500 font-mono">55 Curated Questions</span>
          </div>
          <h2 className="text-xl font-bold text-slate-900 mt-1">B1/B2 Consular Question Bank</h2>
          <p className="text-sm text-slate-600">The 55 seed questions populated via Flyway migration V3 for real-time interview simulations.</p>
        </div>

        <div className="flex flex-wrap gap-2">
          <button
            onClick={() => setSelectedCategory("ALL")}
            className={`px-3 py-1.5 rounded-lg text-xs font-semibold transition-all ${selectedCategory === "ALL" ? "bg-slate-900 text-white" : "bg-slate-100 text-slate-600 hover:bg-slate-200"}`}
          >
            All 10 Categories
          </button>
          <button
            onClick={() => setSelectedCategory("TRAVEL_PURPOSE")}
            className={`px-3 py-1.5 rounded-lg text-xs font-semibold transition-all ${selectedCategory === "TRAVEL_PURPOSE" ? "bg-slate-900 text-white" : "bg-slate-100 text-slate-600 hover:bg-slate-200"}`}
          >
            Purpose
          </button>
          <button
            onClick={() => setSelectedCategory("EMPLOYMENT")}
            className={`px-3 py-1.5 rounded-lg text-xs font-semibold transition-all ${selectedCategory === "EMPLOYMENT" ? "bg-slate-900 text-white" : "bg-slate-100 text-slate-600 hover:bg-slate-200"}`}
          >
            Employment
          </button>
          <button
            onClick={() => setSelectedCategory("FINANCIAL_ABILITY")}
            className={`px-3 py-1.5 rounded-lg text-xs font-semibold transition-all ${selectedCategory === "FINANCIAL_ABILITY" ? "bg-slate-900 text-white" : "bg-slate-100 text-slate-600 hover:bg-slate-200"}`}
          >
            Finances
          </button>
          <button
            onClick={() => setSelectedCategory("TIES_TO_HOME_COUNTRY")}
            className={`px-3 py-1.5 rounded-lg text-xs font-semibold transition-all ${selectedCategory === "TIES_TO_HOME_COUNTRY" ? "bg-slate-900 text-white" : "bg-slate-100 text-slate-600 hover:bg-slate-200"}`}
          >
            Ties to Kenya
          </button>
        </div>
      </div>

      <div className="py-6 space-y-6">
        {filteredCategories.map(cat => (
          <div key={cat.category} className="space-y-3">
            <div className="flex items-center gap-2">
              <span className="text-sm font-bold text-slate-900">{cat.categoryLabel}</span>
              <span className="text-xs text-slate-400 font-mono">({cat.questions.length} questions)</span>
            </div>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-2.5">
              {cat.questions.map((q, idx) => (
                <div key={idx} className="p-3 bg-slate-50 border border-slate-100 rounded-xl text-xs text-slate-800 flex items-start gap-2.5 hover:bg-slate-100/70 transition-colors">
                  <span className="text-blue-600 font-bold shrink-0">Q{idx + 1}.</span>
                  <span className="leading-relaxed">{q}</span>
                </div>
              ))}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
