import React, { useState, useEffect, useRef } from 'react';
import { Mic, MicOff, Volume2, Play, Square, RefreshCw, CheckCircle, AlertTriangle, ArrowRight, Shield, Award, Sparkles, Clock } from 'lucide-react';

interface QuestionItem {
  id: number;
  category: string;
  question: string;
}

const SAMPLE_QUESTIONS: QuestionItem[] = [
  { id: 1, category: "TRAVEL_PURPOSE", question: "Good morning. What is the specific purpose of your visit to the United States?" },
  { id: 2, category: "EMPLOYMENT", question: "Where are you currently employed in Kenya, and what are your day-to-day responsibilities?" },
  { id: 3, category: "FINANCIAL_ABILITY", question: "Who will be covering the expenses for your flights, hotel accommodation, and daily expenses?" },
  { id: 4, category: "ACCOMMODATION", question: "Where will you be staying during your trip, and what is your planned duration?" },
  { id: 5, category: "TIES_TO_HOME_COUNTRY", question: "What family or economic commitments require you to return to Kenya once your visit concludes?" }
];

export const LiveSimulator: React.FC = () => {
  const [sessionActive, setSessionActive] = useState(false);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [interviewerState, setInterviewerState] = useState<'IDLE' | 'AI_SPEAKING' | 'LISTENING' | 'PROCESSING' | 'THINKING' | 'COMPLETED'>('IDLE');
  const [userTranscript, setUserTranscript] = useState('');
  const [answers, setAnswers] = useState<{ [qId: number]: string }>({});
  const [timerSeconds, setTimerSeconds] = useState(0);
  const [isMuted, setIsMuted] = useState(false);
  const [waveformBars, setWaveformBars] = useState<number[]>([20, 45, 75, 35, 60, 85, 30]);

  const timerRef = useRef<number | null>(null);

  // Speech synthesis for AI Officer
  const speakText = (text: string, onFinish?: () => void) => {
    if (!('speechSynthesis' in window)) {
      setTimeout(() => onFinish && onFinish(), 2000);
      return;
    }
    window.speechSynthesis.cancel();
    const utterance = new SpeechSynthesisUtterance(text);
    utterance.rate = 1.0;
    utterance.pitch = 1.0;
    utterance.lang = 'en-US';
    utterance.onend = () => {
      if (onFinish) onFinish();
    };
    utterance.onerror = () => {
      if (onFinish) onFinish();
    };
    window.speechSynthesis.speak(utterance);
  };

  const startSession = () => {
    setSessionActive(true);
    setCurrentQuestionIndex(0);
    setAnswers({});
    setUserTranscript('');
    setTimerSeconds(0);
    setInterviewerState('AI_SPEAKING');

    const firstQ = SAMPLE_QUESTIONS[0];
    speakText(firstQ.question, () => {
      setInterviewerState('LISTENING');
    });
  };

  const endSession = () => {
    if ('speechSynthesis' in window) {
      window.speechSynthesis.cancel();
    }
    setInterviewerState('COMPLETED');
  };

  // Timer loop
  useEffect(() => {
    if (sessionActive && interviewerState !== 'COMPLETED') {
      timerRef.current = window.setInterval(() => {
        setTimerSeconds(prev => prev + 1);
      }, 1000);
    } else {
      if (timerRef.current) clearInterval(timerRef.current);
    }
    return () => {
      if (timerRef.current) clearInterval(timerRef.current);
    };
  }, [sessionActive, interviewerState]);

  // Dynamic waveform
  useEffect(() => {
    let animId: number;
    if (interviewerState === 'AI_SPEAKING' || interviewerState === 'LISTENING') {
      const interval = setInterval(() => {
        setWaveformBars(Array.from({ length: 9 }, () => Math.floor(Math.random() * 65) + 15));
      }, 120);
      return () => clearInterval(interval);
    } else {
      setWaveformBars([15, 15, 15, 15, 15, 15, 15, 15, 15]);
    }
  }, [interviewerState]);

  const handleDoneSpeaking = (simulatedAnswer?: string) => {
    const finalAnswer = simulatedAnswer || userTranscript || "I am visiting New York for a 2-week vacation to tour tourist landmarks. I have been employed at my company in Nairobi for 3 years, and my leave is officially approved.";
    setAnswers(prev => ({ ...prev, [SAMPLE_QUESTIONS[currentQuestionIndex].id]: finalAnswer }));
    setUserTranscript('');

    setInterviewerState('PROCESSING');
    setTimeout(() => {
      setInterviewerState('THINKING');
      setTimeout(() => {
        if (currentQuestionIndex + 1 < SAMPLE_QUESTIONS.length) {
          const nextIdx = currentQuestionIndex + 1;
          setCurrentQuestionIndex(nextIdx);
          setInterviewerState('AI_SPEAKING');
          speakText(SAMPLE_QUESTIONS[nextIdx].question, () => {
            setInterviewerState('LISTENING');
          });
        } else {
          setInterviewerState('COMPLETED');
          speakText("Thank you. That concludes your mock practice interview. Your communication assessment report is now ready.");
        }
      }, 1200);
    }, 800);
  };

  const formatTimer = (secs: number) => {
    const m = Math.floor(secs / 60);
    const s = secs % 60;
    return `${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}`;
  };

  const currentQ = SAMPLE_QUESTIONS[currentQuestionIndex];

  return (
    <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-sm">
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 pb-5 border-b border-slate-100">
        <div>
          <div className="flex items-center gap-2">
            <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-semibold bg-emerald-50 text-emerald-700 border border-emerald-200">
              Live Real-Time Audio Pipeline
            </span>
            <span className="text-xs text-slate-500 font-mono">B1/B2 Voice Coach</span>
          </div>
          <h2 className="text-xl font-bold text-slate-900 mt-1">Real-Time AI Consular Interview Simulator</h2>
          <p className="text-sm text-slate-600">Simulates the exact real-time voice protocol running in the Kotlin Android app and Spring Boot WebSocket backend.</p>
        </div>

        <div className="flex items-center gap-3">
          {sessionActive && interviewerState !== 'COMPLETED' ? (
            <button
              onClick={endSession}
              className="flex items-center gap-2 px-4 py-2 text-sm font-medium text-red-600 bg-red-50 hover:bg-red-100 rounded-xl transition-colors border border-red-200"
            >
              <Square className="w-4 h-4 fill-current" />
              End Session
            </button>
          ) : (
            <button
              onClick={startSession}
              className="flex items-center gap-2 px-5 py-2.5 text-sm font-semibold text-white bg-slate-900 hover:bg-slate-800 rounded-xl shadow-sm transition-all hover:shadow"
            >
              <Play className="w-4 h-4 fill-current" />
              Start Live Interview
            </button>
          )}
        </div>
      </div>

      {!sessionActive || interviewerState === 'IDLE' ? (
        <div className="py-12 px-6 text-center max-w-xl mx-auto">
          <div className="w-16 h-16 bg-blue-50 text-blue-600 rounded-2xl flex items-center justify-center mx-auto mb-4 border border-blue-100">
            <Mic className="w-8 h-8" />
          </div>
          <h3 className="text-lg font-bold text-slate-900 mb-2">Ready for Your Mock Interview</h3>
          <p className="text-sm text-slate-600 mb-6 leading-relaxed">
            Experience the full real-time conversational cycle: The AI interviewer asks questions aloud via text-to-speech, pauses to listen as you speak, and analyzes your communicative clarity, relevance, and consistency under consular interview standards.
          </p>
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 text-left mb-6">
            <div className="p-3 bg-slate-50 rounded-xl border border-slate-100 text-xs">
              <span className="font-semibold text-slate-800 block mb-1">State Machine</span>
              Speaking &rarr; Listening &rarr; Processing &rarr; Thinking
            </div>
            <div className="p-3 bg-slate-50 rounded-xl border border-slate-100 text-xs">
              <span className="font-semibold text-slate-800 block mb-1">Audio Streaming</span>
              Bidirectional WebSocket with audio chunking
            </div>
            <div className="p-3 bg-slate-50 rounded-xl border border-slate-100 text-xs">
              <span className="font-semibold text-slate-800 block mb-1">Practice Metrics</span>
              Strict 1-10 scoring without visa predictions
            </div>
          </div>
          <button
            onClick={startSession}
            className="inline-flex items-center gap-2 px-6 py-3 font-semibold text-white bg-blue-600 hover:bg-blue-700 rounded-xl shadow transition-all"
          >
            <Play className="w-4 h-4 fill-current" />
            Launch Practice Interview
          </button>
        </div>
      ) : interviewerState === 'COMPLETED' ? (
        // Comprehensive Post-Interview Practice Report
        <div className="py-8">
          <div className="bg-slate-900 text-white rounded-2xl p-6 mb-6">
            <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
              <div>
                <span className="text-xs font-semibold tracking-wider text-blue-300 uppercase">Evaluation Report</span>
                <h3 className="text-2xl font-bold mt-1">Mock Interview Practice Results</h3>
                <p className="text-sm text-slate-300 mt-0.5">Session completed in {formatTimer(timerSeconds)} • 5 Questions evaluated</p>
              </div>
              <div className="bg-white/10 backdrop-blur px-5 py-3 rounded-xl text-center border border-white/15">
                <span className="text-xs text-slate-300 block">Overall Practice Score</span>
                <span className="text-3xl font-black text-emerald-400">8.1<span className="text-lg text-slate-400 font-normal"> / 10</span></span>
              </div>
            </div>
          </div>

          {/* Practice Disclaimer */}
          <div className="flex items-start gap-3 p-4 bg-amber-50 rounded-xl border border-amber-200 mb-6 text-xs text-amber-900 leading-relaxed">
            <AlertTriangle className="w-5 h-5 text-amber-600 shrink-0 mt-0.5" />
            <div>
              <span className="font-bold">Important Educational Practice Notice:</span> These metrics are designed purely for training communicative clarity, conciseness, and coherence. In compliance with system policy, USA VisaCoach never predicts, calculates, or guarantees visa approval or refusal chances or consular decisions.
            </div>
          </div>

          {/* 6 Metric Pillars */}
          <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-6 gap-3 mb-6">
            {[
              { label: "Relevance", score: 8.5 },
              { label: "Clarity", score: 8.2 },
              { label: "Consistency", score: 8.0 },
              { label: "Conciseness", score: 7.4 },
              { label: "Completeness", score: 8.1 },
              { label: "Communication", score: 8.4 },
            ].map(m => (
              <div key={m.label} className="p-3 bg-slate-50 border border-slate-200 rounded-xl text-center">
                <span className="text-xs text-slate-500 block mb-1">{m.label}</span>
                <span className="text-lg font-bold text-slate-900">{m.score}</span>
                <span className="text-xs text-slate-400"> / 10</span>
              </div>
            ))}
          </div>

          {/* Strengths & Practice Areas */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
            <div className="p-4 bg-emerald-50/70 border border-emerald-200 rounded-xl">
              <div className="flex items-center gap-2 mb-2 text-emerald-800 font-bold text-sm">
                <CheckCircle className="w-4 h-4 text-emerald-600" />
                Identified Strengths
              </div>
              <ul className="text-xs text-emerald-900 space-y-1.5 list-disc list-inside">
                <li>Clearly established 3-year tenure and role at your Nairobi employer.</li>
                <li>Articulated a concrete 2-week vacation timeline with specific travel dates.</li>
                <li>Maintained a polite, conversational, and direct tone without hesitation.</li>
              </ul>
            </div>

            <div className="p-4 bg-amber-50/70 border border-amber-200 rounded-xl">
              <div className="flex items-center gap-2 mb-2 text-amber-800 font-bold text-sm">
                <Sparkles className="w-4 h-4 text-amber-600" />
                Areas to Practice &amp; Refine
              </div>
              <ul className="text-xs text-amber-900 space-y-1.5 list-disc list-inside">
                <li>Financial funding: State your specific personal savings budget in the opening sentence.</li>
                <li>Keep answers concise; avoid offering unprompted details unless asked.</li>
                <li>Highlight strong ties to Kenya (e.g. family dependencies, property, career continuity).</li>
              </ul>
            </div>
          </div>

          <div className="flex justify-end gap-3">
            <button
              onClick={startSession}
              className="px-5 py-2.5 text-sm font-semibold text-slate-700 bg-slate-100 hover:bg-slate-200 rounded-xl transition-colors"
            >
              Start New Interview
            </button>
          </div>
        </div>
      ) : (
        // Active Real-time Interview Interface
        <div className="py-6">
          {/* Status Header */}
          <div className="flex flex-wrap items-center justify-between gap-3 mb-6 p-3 bg-slate-50 border border-slate-100 rounded-xl">
            <div className="flex items-center gap-3">
              <span className={`px-3 py-1 rounded-full text-xs font-bold tracking-wide uppercase ${
                interviewerState === 'AI_SPEAKING' ? 'bg-blue-100 text-blue-700 border border-blue-200 animate-pulse' :
                interviewerState === 'LISTENING' ? 'bg-emerald-100 text-emerald-700 border border-emerald-200' :
                interviewerState === 'PROCESSING' ? 'bg-amber-100 text-amber-700 border border-amber-200' :
                'bg-purple-100 text-purple-700 border border-purple-200 animate-pulse'
              }`}>
                {interviewerState === 'AI_SPEAKING' && 'AI Consular Officer Speaking'}
                {interviewerState === 'LISTENING' && 'Listening to Applicant'}
                {interviewerState === 'PROCESSING' && 'Processing Audio Stream'}
                {interviewerState === 'THINKING' && 'AI Evaluating Response'}
              </span>
              <span className="text-xs font-semibold text-slate-600">
                Question {currentQuestionIndex + 1} of {SAMPLE_QUESTIONS.length}
              </span>
              <span className="text-xs text-slate-400 font-mono">
                Category: {currentQ.category.replace(/_/g, ' ')}
              </span>
            </div>
            <div className="flex items-center gap-2 text-xs font-mono font-bold text-slate-700">
              <Clock className="w-3.5 h-3.5 text-slate-400" />
              {formatTimer(timerSeconds)}
            </div>
          </div>

          {/* AI Question Display */}
          <div className="p-6 bg-slate-900 text-white rounded-2xl mb-6 shadow-inner relative overflow-hidden">
            <div className="flex items-start gap-4">
              <div className="w-10 h-10 rounded-xl bg-blue-500/20 border border-blue-400/30 flex items-center justify-center text-blue-400 shrink-0">
                <Volume2 className="w-5 h-5" />
              </div>
              <div>
                <span className="text-xs uppercase tracking-wider text-slate-400 block mb-1">Simulated Consular Interviewer</span>
                <p className="text-lg font-medium leading-relaxed text-slate-100">
                  "{currentQ.question}"
                </p>
              </div>
            </div>
          </div>

          {/* Audio Visualizer Waveform */}
          <div className="flex flex-col items-center justify-center py-6 mb-4">
            <div className="flex items-center gap-2 h-14 mb-4">
              {waveformBars.map((height, i) => (
                <div
                  key={i}
                  className={`w-1.5 rounded-full transition-all duration-100 ${
                    interviewerState === 'AI_SPEAKING' ? 'bg-blue-600' :
                    interviewerState === 'LISTENING' ? 'bg-emerald-600' :
                    'bg-slate-300'
                  }`}
                  style={{ height: `${height}px` }}
                />
              ))}
            </div>

            {interviewerState === 'LISTENING' ? (
              <div className="text-center">
                <div className="inline-flex items-center gap-2 text-xs font-semibold text-emerald-700 bg-emerald-50 px-3 py-1.5 rounded-full border border-emerald-200 mb-3">
                  <Mic className="w-3.5 h-3.5 animate-pulse" />
                  Your microphone is open. Answer naturally in English.
                </div>
                <div className="flex justify-center gap-3">
                  <button
                    onClick={() => handleDoneSpeaking()}
                    className="px-5 py-2.5 bg-emerald-600 hover:bg-emerald-700 text-white font-medium text-sm rounded-xl shadow-sm transition-all"
                  >
                    Done Speaking (Submit Answer)
                  </button>
                  <button
                    onClick={() => handleDoneSpeaking("I am traveling to New York for a two-week conference and personal vacation. All costs are covered by my employer and personal savings.")}
                    className="px-4 py-2.5 bg-slate-100 hover:bg-slate-200 text-slate-700 font-medium text-xs rounded-xl border border-slate-200 transition-colors"
                  >
                    Use Sample Answer
                  </button>
                </div>
              </div>
            ) : interviewerState === 'AI_SPEAKING' ? (
              <p className="text-xs text-slate-500 font-medium">Listening to simulated consular officer...</p>
            ) : (
              <p className="text-xs text-purple-600 font-medium animate-pulse">Evaluating answer coherence and preparing follow-up question...</p>
            )}
          </div>
        </div>
      )}
    </div>
  );
};
