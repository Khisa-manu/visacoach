import React, { useState, useMemo } from 'react';
import { 
  Search, Star, CheckCircle2, AlertTriangle, Info, ChevronDown, ChevronUp, 
  BookOpen, Bookmark, Shield, Sparkles, Filter, X, ArrowLeft, Check, Share2, Copy
} from 'lucide-react';
import { VISA_CATEGORIES, VISA_QUESTIONS, VISA_GUIDES, VisaQuestion, VisaGuideArticle } from '../data/visaQuestions';

interface AndroidInteractivePreviewProps {
  onOpenAbout?: () => void;
}

export const AndroidInteractivePreview: React.FC<AndroidInteractivePreviewProps> = ({ onOpenAbout }) => {
  const [activeTab, setActiveTab] = useState<'QUESTIONS' | 'GUIDES' | 'SAVED'>('QUESTIONS');
  const [selectedCategory, setSelectedCategory] = useState('ALL');
  const [searchQuery, setSearchQuery] = useState('');
  const [expandedId, setExpandedId] = useState<string | null>('tp-01');
  const [bookmarkedIds, setBookmarkedIds] = useState<string[]>(['tp-01', 'tie-01', 'fin-01']);
  const [selectedGuide, setSelectedGuide] = useState<VisaGuideArticle | null>(null);
  const [copiedId, setCopiedId] = useState<string | null>(null);
  const [showPhoneAbout, setShowPhoneAbout] = useState(false);

  const toggleBookmark = (id: string, e?: React.MouseEvent) => {
    e?.stopPropagation();
    setBookmarkedIds(prev => 
      prev.includes(id) ? prev.filter(item => item !== id) : [...prev, id]
    );
  };

  const handleCopy = (text: string, id: string, e?: React.MouseEvent) => {
    e?.stopPropagation();
    navigator.clipboard.writeText(text);
    setCopiedId(id);
    setTimeout(() => setCopiedId(null), 2000);
  };

  const filteredQuestions = useMemo(() => {
    const query = searchQuery.toLowerCase().trim();
    return VISA_QUESTIONS.filter(q => {
      const matchesCategory = selectedCategory === 'ALL' || q.category === selectedCategory;
      const matchesQuery = !query || 
        q.question.toLowerCase().includes(query) ||
        q.sampleAnswer.toLowerCase().includes(query) ||
        q.officerIntent.toLowerCase().includes(query) ||
        q.shortSummary.toLowerCase().includes(query);
      return matchesCategory && matchesQuery;
    });
  }, [searchQuery, selectedCategory]);

  const savedQuestions = useMemo(() => {
    return VISA_QUESTIONS.filter(q => bookmarkedIds.includes(q.id));
  }, [bookmarkedIds]);

  return (
    <div className="flex flex-col lg:flex-row gap-8 items-start justify-center">
      {/* Phone Frame Device Container */}
      <div className="w-full max-w-[390px] mx-auto bg-slate-900 p-3.5 rounded-[44px] shadow-2xl border-4 border-slate-800 ring-1 ring-slate-700/50">
        {/* Dynamic Island / Speaker Notch */}
        <div className="relative flex items-center justify-center h-5 mb-1">
          <div className="w-24 h-4 bg-black rounded-full flex items-center justify-center gap-2">
            <div className="w-2 h-2 rounded-full bg-slate-800" />
            <div className="w-2.5 h-2.5 rounded-full bg-slate-900 border border-slate-700" />
          </div>
        </div>

        {/* Screen Bezel */}
        <div className="bg-[#F8FAFC] rounded-[34px] overflow-hidden flex flex-col h-[740px] text-slate-900 select-none relative shadow-inner">
          {/* Status Bar */}
          <div className="bg-[#0A2540] text-white px-5 py-2 flex items-center justify-between text-[11px] font-medium tracking-tight">
            <span>9:41</span>
            <div className="flex items-center gap-1.5">
              <span className="text-[10px] uppercase font-bold text-teal-300">Offline Mode</span>
              <div className="w-4 h-2.5 border border-white/80 rounded-sm p-0.5 flex items-center">
                <div className="h-full w-full bg-white rounded-2xs" />
              </div>
            </div>
          </div>

          {/* App Header Bar */}
          <div className="bg-[#0A2540] text-white px-4 py-3 shadow-sm flex items-center justify-between border-b border-slate-800">
            <div>
              <h2 className="font-bold text-sm tracking-tight flex items-center gap-1.5">
                <span>USA Visa Interview Guide</span>
              </h2>
              <p className="text-[11px] text-teal-400 font-medium">
                68 Verified Questions &amp; Answers • Static
              </p>
            </div>
            <div className="flex items-center gap-2">
              <button
                onClick={() => setShowPhoneAbout(true)}
                className="w-7 h-7 rounded-lg bg-slate-800 hover:bg-slate-700 text-teal-300 flex items-center justify-center transition-colors"
                title="About Paperglow systems"
              >
                <Info className="w-3.5 h-3.5" />
              </button>
              <div className="px-2 py-1 bg-teal-500/20 text-teal-300 rounded text-[10px] font-bold uppercase tracking-wider border border-teal-400/30">
                INA 214(b)
              </div>
            </div>
          </div>

          {/* In-Phone About Dialog Overlay */}
          {showPhoneAbout && (
            <div className="absolute inset-0 z-50 bg-black/60 backdrop-blur-xs flex items-center justify-center p-4">
              <div className="bg-white rounded-2xl p-5 shadow-xl max-w-[320px] w-full space-y-4 text-center animate-in fade-in zoom-in duration-150">
                <div className="w-12 h-12 bg-[#0A2540] text-white rounded-2xl flex items-center justify-center font-black text-lg mx-auto shadow-sm">
                  PS
                </div>
                <div>
                  <span className="text-[10px] font-bold text-teal-700 bg-teal-50 px-2 py-0.5 rounded-full border border-teal-200">
                    Version 1.0 (Static Offline)
                  </span>
                  <h3 className="font-bold text-base text-slate-900 mt-1">
                    USA Visa Interview Guide
                  </h3>
                  <p className="text-xs font-semibold text-teal-700 mt-0.5">
                    Developed by Paperglow systems
                  </p>
                </div>
                <p className="text-[11px] text-slate-600 leading-relaxed text-left bg-slate-50 p-3 rounded-xl border border-slate-100">
                  A standalone consular interview manual featuring 68 questions, 30-second model answers, and Section 214(b) doctrine. No tracking, no ads, and 100% offline.
                </p>
                <div className="space-y-2 pt-1">
                  {onOpenAbout && (
                    <button
                      onClick={() => {
                        setShowPhoneAbout(false);
                        onOpenAbout();
                      }}
                      className="w-full py-2 bg-[#0A2540] text-white text-xs font-semibold rounded-xl hover:bg-slate-800 transition-colors"
                    >
                      View Full System Details
                    </button>
                  )}
                  <button
                    onClick={() => setShowPhoneAbout(false)}
                    className="w-full py-2 bg-slate-100 text-slate-700 text-xs font-semibold rounded-xl hover:bg-slate-200 transition-colors"
                  >
                    Close
                  </button>
                </div>
              </div>
            </div>
          )}

          {/* Main Scrollable Viewport */}
          <div className="flex-1 overflow-y-auto overscroll-contain">
            {activeTab === 'QUESTIONS' && (
              <div className="p-3 space-y-3">
                {/* Search Bar */}
                <div className="relative">
                  <Search className="w-4 h-4 text-slate-400 absolute left-3 top-3" />
                  <input
                    type="text"
                    placeholder="Search 68 questions & answers..."
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    className="w-full pl-9 pr-8 py-2 bg-white text-xs text-slate-800 placeholder-slate-400 rounded-xl border border-slate-200 focus:outline-none focus:border-[#0A2540] shadow-xs"
                  />
                  {searchQuery && (
                    <button
                      onClick={() => setSearchQuery('')}
                      className="absolute right-2.5 top-2.5 text-slate-400 hover:text-slate-600"
                    >
                      <X className="w-3.5 h-3.5" />
                    </button>
                  )}
                </div>

                {/* Category Pills (Horizontal Scroll) */}
                <div className="flex gap-1.5 overflow-x-auto pb-1 no-scrollbar text-[11px]">
                  {VISA_CATEGORIES.map(cat => (
                    <button
                      key={cat.id}
                      onClick={() => setSelectedCategory(cat.id)}
                      className={`whitespace-nowrap px-2.5 py-1 rounded-lg font-medium transition-colors ${
                        selectedCategory === cat.id
                          ? 'bg-[#0A2540] text-white shadow-xs'
                          : 'bg-white text-slate-600 border border-slate-200 hover:bg-slate-100'
                      }`}
                    >
                      {cat.name} ({cat.count})
                    </button>
                  ))}
                </div>

                {/* Questions List */}
                <div className="space-y-2.5">
                  <div className="flex items-center justify-between text-[11px] font-semibold text-slate-500 px-1">
                    <span>{filteredQuestions.length} Questions Found</span>
                    {searchQuery && <span>Filter: "{searchQuery}"</span>}
                  </div>

                  {filteredQuestions.length === 0 ? (
                    <div className="text-center py-12 px-4 bg-white rounded-2xl border border-dashed border-slate-300">
                      <Search className="w-8 h-8 text-slate-300 mx-auto mb-2" />
                      <p className="text-xs font-semibold text-slate-700">No questions matched</p>
                      <p className="text-[11px] text-slate-500 mt-1">Try keywords like "salary", "job", "ties", or "family".</p>
                    </div>
                  ) : (
                    filteredQuestions.map((item) => {
                      const isExpanded = expandedId === item.id;
                      const isBookmarked = bookmarkedIds.includes(item.id);

                      return (
                        <div
                          key={item.id}
                          className={`bg-white rounded-xl border transition-all ${
                            isExpanded ? 'border-teal-500/40 shadow-sm ring-1 ring-teal-500/20' : 'border-slate-200 hover:border-slate-300'
                          }`}
                        >
                          {/* Card Header (Always Visible) */}
                          <div 
                            className="p-3 cursor-pointer"
                            onClick={() => setExpandedId(isExpanded ? null : item.id)}
                          >
                            <div className="flex items-center justify-between gap-2 mb-1.5">
                              <span className={`text-[10px] font-bold px-2 py-0.5 rounded ${
                                item.category === 'RED_FLAGS' 
                                  ? 'bg-rose-50 text-rose-700 border border-rose-200' 
                                  : item.category === 'TIES_214B'
                                  ? 'bg-emerald-50 text-emerald-700 border border-emerald-200'
                                  : 'bg-sky-50 text-sky-700 border border-sky-200'
                              }`}>
                                {item.categoryName}
                              </span>

                              <div className="flex items-center gap-1">
                                <button
                                  onClick={(e) => handleCopy(`${item.question}\n\nModel Answer:\n${item.sampleAnswer}`, item.id, e)}
                                  className="p-1 text-slate-400 hover:text-slate-600 rounded"
                                  title="Copy question and answer"
                                >
                                  {copiedId === item.id ? (
                                    <Check className="w-3.5 h-3.5 text-emerald-600" />
                                  ) : (
                                    <Copy className="w-3.5 h-3.5" />
                                  )}
                                </button>
                                <button
                                  onClick={(e) => toggleBookmark(item.id, e)}
                                  className="p-1 rounded text-slate-400 hover:text-amber-500 transition-colors"
                                  title={isBookmarked ? "Remove from bookmarks" : "Save question"}
                                >
                                  <Star className={`w-3.5 h-3.5 ${isBookmarked ? 'fill-amber-400 text-amber-500' : ''}`} />
                                </button>
                              </div>
                            </div>

                            <h4 className="text-xs font-bold text-slate-900 leading-snug">
                              {item.question}
                            </h4>

                            {!isExpanded && (
                              <div className="mt-2 flex items-center justify-between text-[11px] text-slate-500">
                                <span className="line-clamp-1">{item.shortSummary}</span>
                                <span className="text-teal-600 font-semibold flex items-center text-[10px] shrink-0">
                                  View Answer <ChevronDown className="w-3 h-3 ml-0.5" />
                                </span>
                              </div>
                            )}
                          </div>

                          {/* Expanded Content View */}
                          {isExpanded && (
                            <div className="px-3 pb-3 pt-1 border-t border-slate-100 space-y-2.5 text-xs">
                              {/* Model Answer Box */}
                              <div className="bg-emerald-50/80 rounded-lg p-2.5 border border-emerald-200/80">
                                <div className="flex items-center gap-1.5 text-emerald-800 font-bold text-[11px] mb-1">
                                  <CheckCircle2 className="w-3.5 h-3.5 text-emerald-600" />
                                  <span>RECOMMENDED MODEL ANSWER</span>
                                </div>
                                <p className="text-slate-800 text-[11.5px] leading-relaxed italic">
                                  "{item.sampleAnswer}"
                                </p>
                              </div>

                              {/* Officer Intent Box */}
                              <div className="bg-sky-50/80 rounded-lg p-2.5 border border-sky-200/80">
                                <div className="flex items-center gap-1.5 text-sky-800 font-bold text-[11px] mb-1">
                                  <Info className="w-3.5 h-3.5 text-sky-600" />
                                  <span>WHAT THE OFFICER IS REALLY TESTING</span>
                                </div>
                                <p className="text-slate-700 text-[11px] leading-relaxed">
                                  {item.officerIntent}
                                </p>
                              </div>

                              {/* Red Flags Traps */}
                              <div className="bg-rose-50/80 rounded-lg p-2.5 border border-rose-200/80">
                                <div className="flex items-center gap-1.5 text-rose-800 font-bold text-[11px] mb-1">
                                  <AlertTriangle className="w-3.5 h-3.5 text-rose-600" />
                                  <span>TRAPS &amp; WHAT NOT TO SAY</span>
                                </div>
                                <ul className="space-y-1 text-[11px] text-rose-950">
                                  {item.redFlags.map((flag, idx) => (
                                    <li key={idx} className="flex items-start gap-1.5">
                                      <span className="text-rose-500 font-bold leading-tight">•</span>
                                      <span>{flag}</span>
                                    </li>
                                  ))}
                                </ul>
                              </div>

                              {/* Pro Tips */}
                              <div className="bg-slate-50 rounded-lg p-2 border border-slate-200 text-[10.5px] text-slate-600">
                                <span className="font-bold text-slate-700">Pro Tip: </span>
                                {item.tips.join(' • ')}
                              </div>

                              <button
                                onClick={() => setExpandedId(null)}
                                className="w-full py-1 text-center text-[10px] font-semibold text-slate-400 hover:text-slate-600 flex items-center justify-center gap-0.5"
                              >
                                Collapse <ChevronUp className="w-3 h-3" />
                              </button>
                            </div>
                          )}
                        </div>
                      );
                    })
                  )}
                </div>
              </div>
            )}

            {activeTab === 'GUIDES' && (
              <div className="p-3 space-y-3">
                {selectedGuide ? (
                  <div className="space-y-3">
                    <button
                      onClick={() => setSelectedGuide(null)}
                      className="flex items-center gap-1 text-xs font-bold text-[#0A2540] hover:underline"
                    >
                      <ArrowLeft className="w-3.5 h-3.5" /> Back to Guides
                    </button>

                    <div className="bg-white rounded-xl p-3.5 border border-slate-200 shadow-xs space-y-2">
                      <div className="flex items-center justify-between text-[10px] text-slate-500">
                        <span className="font-bold text-teal-600 uppercase">{selectedGuide.category}</span>
                        <span>{selectedGuide.readTime}</span>
                      </div>
                      <h3 className="font-bold text-sm text-slate-900 leading-tight">
                        {selectedGuide.title}
                      </h3>
                      <p className="text-xs text-slate-600 italic">
                        {selectedGuide.summary}
                      </p>

                      {/* Key Takeaways */}
                      <div className="bg-emerald-50 rounded-lg p-2.5 border border-emerald-200 text-xs">
                        <div className="font-bold text-emerald-800 text-[11px] mb-1">KEY TAKEAWAYS</div>
                        <ul className="space-y-1 text-[11px] text-emerald-950">
                          {selectedGuide.keyPoints.map((pt, i) => (
                            <li key={i} className="flex items-start gap-1.5">
                              <Check className="w-3 h-3 text-emerald-600 shrink-0 mt-0.5" />
                              <span>{pt}</span>
                            </li>
                          ))}
                        </ul>
                      </div>

                      {/* Article Paragraphs */}
                      <div className="space-y-2 pt-2 border-t border-slate-100 text-xs text-slate-700 leading-relaxed">
                        {selectedGuide.content.map((p, idx) => (
                          <p key={idx}>{p}</p>
                        ))}
                      </div>
                    </div>
                  </div>
                ) : (
                  <div className="space-y-2.5">
                    <div className="px-1">
                      <h3 className="text-xs font-bold text-slate-900">Essential Embassy Guides</h3>
                      <p className="text-[11px] text-slate-500">Master the legal and psychological rules of the consular window.</p>
                    </div>

                    {VISA_GUIDES.map(guide => (
                      <div
                        key={guide.id}
                        onClick={() => setSelectedGuide(guide)}
                        className="bg-white p-3 rounded-xl border border-slate-200 hover:border-slate-300 cursor-pointer shadow-2xs space-y-1.5"
                      >
                        <div className="flex items-center justify-between text-[10px]">
                          <span className="font-bold text-teal-600 uppercase">{guide.category}</span>
                          <span className="text-slate-400">{guide.readTime}</span>
                        </div>
                        <h4 className="font-bold text-xs text-slate-900">{guide.title}</h4>
                        <p className="text-[11px] text-slate-500 line-clamp-2 leading-tight">{guide.summary}</p>
                        <div className="pt-1 flex items-center text-[11px] font-semibold text-[#0A2540]">
                          Read Guide →
                        </div>
                      </div>
                    ))}
                  </div>
                )}
              </div>
            )}

            {activeTab === 'SAVED' && (
              <div className="p-3 space-y-3">
                <div className="flex items-center justify-between px-1">
                  <div>
                    <h3 className="text-xs font-bold text-slate-900">Saved for Quick Revision</h3>
                    <p className="text-[11px] text-slate-500">{savedQuestions.length} bookmarked questions</p>
                  </div>
                </div>

                {savedQuestions.length === 0 ? (
                  <div className="text-center py-16 px-4 bg-white rounded-2xl border border-dashed border-slate-300">
                    <Bookmark className="w-8 h-8 text-slate-300 mx-auto mb-2" />
                    <p className="text-xs font-semibold text-slate-700">No questions saved yet</p>
                    <p className="text-[11px] text-slate-500 mt-1">Tap the star icon on any question to bookmark it for fast review before your interview.</p>
                    <button
                      onClick={() => setActiveTab('QUESTIONS')}
                      className="mt-4 px-3 py-1.5 bg-[#0A2540] text-white text-xs rounded-lg font-semibold"
                    >
                      Browse Questions
                    </button>
                  </div>
                ) : (
                  <div className="space-y-2.5">
                    {savedQuestions.map(item => (
                      <div
                        key={item.id}
                        className="bg-white rounded-xl border border-slate-200 p-3 space-y-2"
                      >
                        <div className="flex items-center justify-between">
                          <span className="text-[10px] font-bold px-2 py-0.5 bg-slate-100 text-slate-700 rounded">
                            {item.categoryName}
                          </span>
                          <button
                            onClick={() => toggleBookmark(item.id)}
                            className="text-amber-500 p-1 hover:text-slate-400"
                            title="Remove from saved"
                          >
                            <Star className="w-3.5 h-3.5 fill-amber-400" />
                          </button>
                        </div>
                        <h4 className="text-xs font-bold text-slate-900">{item.question}</h4>
                        <div className="bg-emerald-50 p-2 rounded-lg text-[11px] text-emerald-950">
                          <span className="font-bold text-emerald-800">Answer: </span>
                          "{item.sampleAnswer}"
                        </div>
                        <div className="bg-rose-50 p-2 rounded-lg text-[10.5px] text-rose-950">
                          <span className="font-bold text-rose-800">Trap to avoid: </span>
                          {item.redFlags[0]}
                        </div>
                      </div>
                    ))}
                  </div>
                )}
              </div>
            )}
          </div>

          {/* Bottom App Navigation Bar */}
          <div className="bg-white border-t border-slate-200 px-3 py-2 flex items-center justify-around text-[10px] font-medium text-slate-600">
            <button
              onClick={() => { setActiveTab('QUESTIONS'); setSelectedGuide(null); }}
              className={`flex flex-col items-center gap-0.5 ${activeTab === 'QUESTIONS' ? 'text-[#0A2540] font-bold' : 'hover:text-slate-900'}`}
            >
              <Search className="w-4 h-4" />
              <span>Questions</span>
            </button>
            <button
              onClick={() => { setActiveTab('GUIDES'); setSelectedGuide(null); }}
              className={`flex flex-col items-center gap-0.5 ${activeTab === 'GUIDES' ? 'text-[#0A2540] font-bold' : 'hover:text-slate-900'}`}
            >
              <BookOpen className="w-4 h-4" />
              <span>Guides</span>
            </button>
            <button
              onClick={() => { setActiveTab('SAVED'); setSelectedGuide(null); }}
              className={`flex flex-col items-center gap-0.5 relative ${activeTab === 'SAVED' ? 'text-[#0A2540] font-bold' : 'hover:text-slate-900'}`}
            >
              <Star className="w-4 h-4" />
              <span>Saved</span>
              {bookmarkedIds.length > 0 && (
                <span className="absolute -top-1 right-1 w-3.5 h-3.5 bg-amber-500 text-white rounded-full text-[9px] flex items-center justify-center font-bold">
                  {bookmarkedIds.length}
                </span>
              )}
            </button>
          </div>
        </div>
      </div>

      {/* Feature Highlights & Specifications Panel */}
      <div className="w-full lg:max-w-xl space-y-6">
        <div className="bg-white rounded-2xl p-6 border border-slate-200 shadow-xs space-y-4">
          <div className="flex items-center gap-2">
            <span className="px-2.5 py-1 bg-teal-50 text-teal-700 text-xs font-bold rounded-lg border border-teal-200">
              Static • Standalone • Offline
            </span>
            <span className="text-xs text-slate-500">Zero Server or Cloud Dependency</span>
          </div>

          <h2 className="text-2xl font-bold text-slate-900">
            USA Visa Interview Guide &amp; Question Bank
          </h2>

          <p className="text-slate-600 text-sm leading-relaxed">
            A static, simple, high-impact mobile preparation manual for applicants attending nonimmigrant visa interviews (B1/B2 tourist/business, F1 students, and visitors). Runs completely offline on Android without requiring servers, AI accounts, or payment gateways.
          </p>

          <div className="grid grid-cols-2 gap-3 pt-2">
            <div className="p-3 bg-slate-50 rounded-xl border border-slate-100">
              <div className="text-xl font-bold text-slate-900">68</div>
              <div className="text-xs text-slate-600 font-medium">Real Embassy Questions</div>
              <div className="text-[11px] text-slate-400">With verified model answers</div>
            </div>
            <div className="p-3 bg-slate-50 rounded-xl border border-slate-100">
              <div className="text-xl font-bold text-slate-900">7</div>
              <div className="text-xs text-slate-600 font-medium">Core Categories</div>
              <div className="text-[11px] text-slate-400">Purpose, Ties, Finances &amp; Traps</div>
            </div>
            <div className="p-3 bg-slate-50 rounded-xl border border-slate-100">
              <div className="text-xl font-bold text-slate-900">100%</div>
              <div className="text-xs text-slate-600 font-medium">Offline Native Speed</div>
              <div className="text-[11px] text-slate-400">Instant search &amp; zero lag</div>
            </div>
            <div className="p-3 bg-slate-50 rounded-xl border border-slate-100">
              <div className="text-xl font-bold text-slate-900">0 KB</div>
              <div className="text-xs text-slate-600 font-medium">Server Costs</div>
              <div className="text-[11px] text-slate-400">Pure client-side APK</div>
            </div>
          </div>

          {/* Developer Attribution Card */}
          <div className="pt-3 border-t border-slate-100 flex items-center justify-between">
            <div className="flex items-center gap-2.5">
              <div className="w-8 h-8 rounded-lg bg-[#0A2540] text-white flex items-center justify-center font-bold text-xs shadow-2xs">
                PS
              </div>
              <div>
                <div className="text-[10px] uppercase font-bold text-slate-400 tracking-wider">Engineered By</div>
                <div className="text-xs font-bold text-slate-900">Developed by Paperglow systems</div>
              </div>
            </div>
            {onOpenAbout && (
              <button
                onClick={onOpenAbout}
                className="text-xs font-semibold text-teal-700 hover:text-teal-800 bg-teal-50 hover:bg-teal-100 px-2.5 py-1 rounded-lg border border-teal-200 transition-colors"
              >
                About &rarr;
              </button>
            )}
          </div>
        </div>

        {/* Why Static & Simple is Best */}
        <div className="bg-white rounded-2xl p-6 border border-slate-200 shadow-xs space-y-4">
          <h3 className="font-bold text-base text-slate-900">What Makes This App Effective</h3>

          <div className="space-y-3 text-xs text-slate-600 leading-relaxed">
            <div className="flex items-start gap-3">
              <div className="w-6 h-6 rounded-lg bg-emerald-100 text-emerald-800 flex items-center justify-center font-bold text-xs shrink-0 mt-0.5">
                ✓
              </div>
              <div>
                <strong className="text-slate-800 block text-xs">Model Answers for Every Question:</strong>
                Real responses crafted to be under 30 seconds, factual, and persuasive without sounding robotic or scripted.
              </div>
            </div>

            <div className="flex items-start gap-3">
              <div className="w-6 h-6 rounded-lg bg-sky-100 text-sky-800 flex items-center justify-center font-bold text-xs shrink-0 mt-0.5">
                ℹ
              </div>
              <div>
                <strong className="text-slate-800 block text-xs">Officer Intent Explained:</strong>
                Understand the psychological and legal test behind every question (e.g., INA 214(b) presumption of immigrant intent, DS-160 consistency).
              </div>
            </div>

            <div className="flex items-start gap-3">
              <div className="w-6 h-6 rounded-lg bg-rose-100 text-rose-800 flex items-center justify-center font-bold text-xs shrink-0 mt-0.5">
                ⚠
              </div>
              <div>
                <strong className="text-slate-800 block text-xs">Red Flags &amp; Common Pitfalls:</strong>
                Explicit warnings on what NOT to say, helping applicants avoid common phrases that trigger automatic refusals.
              </div>
            </div>

            <div className="flex items-start gap-3">
              <div className="w-6 h-6 rounded-lg bg-amber-100 text-amber-800 flex items-center justify-center font-bold text-xs shrink-0 mt-0.5">
                ★
              </div>
              <div>
                <strong className="text-slate-800 block text-xs">Saved Quick Revision:</strong>
                Bookmark difficult questions for rapid review in the waiting room or embassy line on interview morning.
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
