package com.visacoach.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.visacoach.data.QuestionCategory
import com.visacoach.data.VisaGuideItem
import com.visacoach.data.VisaQuestionItem
import com.visacoach.data.VisaQuestionsRepository
import com.visacoach.ui.theme.VisaCoachTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VisaCoachTheme {
                VisaStaticApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisaStaticApp() {
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("visa_bookmarks", Context.MODE_PRIVATE) }
    
    // State for bookmarked question IDs
    val bookmarkedIds = remember {
        mutableStateListOf<String>().apply {
            addAll(sharedPrefs.getStringSet("bookmarked_ids", emptySet()) ?: emptySet())
        }
    }

    fun toggleBookmark(id: String) {
        if (bookmarkedIds.contains(id)) {
            bookmarkedIds.remove(id)
        } else {
            bookmarkedIds.add(id)
        }
        sharedPrefs.edit().putStringSet("bookmarked_ids", bookmarkedIds.toSet()).apply()
    }

    var selectedTab by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(QuestionCategory.ALL) }
    var expandedQuestionId by remember { mutableStateOf<String?>(null) }
    var selectedGuide by remember { mutableStateOf<VisaGuideItem?>(null) }
    var showAboutDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "USA Visa Interview Guide",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Developed by Paperglow systems",
                            fontSize = 11.sp,
                            color = Color(0xFF00A699)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showAboutDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0A2540)
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.List, contentDescription = "Questions") },
                    label = { Text("Questions") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF0A2540),
                        indicatorColor = Color(0xFFE2E8F0)
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Info, contentDescription = "Guides") },
                    label = { Text("Guides") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF0A2540),
                        indicatorColor = Color(0xFFE2E8F0)
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        BadgedBox(badge = {
                            if (bookmarkedIds.isNotEmpty()) {
                                Badge { Text("${bookmarkedIds.size}") }
                            }
                        }) {
                            Icon(Icons.Default.Star, contentDescription = "Saved")
                        }
                    },
                    label = { Text("Saved") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF0A2540),
                        indicatorColor = Color(0xFFE2E8F0)
                    )
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8FAFC))
        ) {
            when (selectedTab) {
                0 -> QuestionsTab(
                    searchQuery = searchQuery,
                    onSearchChange = { searchQuery = it },
                    selectedCategory = selectedCategory,
                    onCategorySelect = { selectedCategory = it },
                    expandedQuestionId = expandedQuestionId,
                    onToggleExpand = { id ->
                        expandedQuestionId = if (expandedQuestionId == id) null else id
                    },
                    bookmarkedIds = bookmarkedIds,
                    onToggleBookmark = { toggleBookmark(it) }
                )
                1 -> GuidesTab(
                    selectedGuide = selectedGuide,
                    onSelectGuide = { selectedGuide = it }
                )
                2 -> BookmarksTab(
                    bookmarkedIds = bookmarkedIds,
                    expandedQuestionId = expandedQuestionId,
                    onToggleExpand = { id ->
                        expandedQuestionId = if (expandedQuestionId == id) null else id
                    },
                    onToggleBookmark = { toggleBookmark(it) },
                    onNavigateToQuestions = { selectedTab = 0 }
                )
            }
        }

        if (showAboutDialog) {
            AlertDialog(
                onDismissRequest = { showAboutDialog = false },
                title = {
                    Text(
                        text = "About USA Visa Guide",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0A2540)
                    )
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Developed by Paperglow systems",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF0A2540)
                        )
                        Text(
                            text = "Version 1.0 (Static Offline Edition)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF0D9488)
                        )
                        Text(
                            text = "A 100% offline, privacy-first consular interview preparation reference manual. Includes 68 embassy questions, 30-second model answers, and Section 214(b) strategic guides.",
                            fontSize = 12.sp,
                            color = Color(0xFF475569),
                            lineHeight = 16.sp
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showAboutDialog = false }) {
                        Text("Close", fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    }
}

@Composable
fun QuestionsTab(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    selectedCategory: QuestionCategory,
    onCategorySelect: (QuestionCategory) -> Unit,
    expandedQuestionId: String?,
    onToggleExpand: (String) -> Unit,
    bookmarkedIds: List<String>,
    onToggleBookmark: (String) -> Unit
) {
    val filteredQuestions = remember(searchQuery, selectedCategory) {
        VisaQuestionsRepository.searchQuestions(searchQuery, selectedCategory)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search Input
        Surface(
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = { Text("Search questions, answers, red flags...", fontSize = 14.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchChange("") }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear")
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF0A2540),
                        unfocusedBorderColor = Color(0xFFE2E8F0)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Category Chips (Horizontal Scrollable)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuestionCategory.values().forEach { category ->
                        val isSelected = selectedCategory == category
                        FilterChip(
                            selected = isSelected,
                            onClick = { onCategorySelect(category) },
                            label = {
                                Text(
                                    text = category.title,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF0A2540),
                                selectedLabelColor = Color.White,
                                containerColor = Color(0xFFF1F5F9),
                                labelColor = Color(0xFF334155)
                            )
                        )
                    }
                }
            }
        }

        // Questions List
        if (filteredQuestions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "No questions match your search",
                        color = Color.Gray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "${filteredQuestions.size} Questions Available",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF64748B),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                items(filteredQuestions, key = { it.id }) { item ->
                    QuestionCard(
                        item = item,
                        isExpanded = expandedQuestionId == item.id,
                        isBookmarked = bookmarkedIds.contains(item.id),
                        onToggleExpand = { onToggleExpand(item.id) },
                        onToggleBookmark = { onToggleBookmark(item.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionCard(
    item: VisaQuestionItem,
    isExpanded: Boolean,
    isBookmarked: Boolean,
    onToggleExpand: () -> Unit,
    onToggleBookmark: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleExpand() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Category Pill & Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = when (item.category) {
                        QuestionCategory.RED_FLAGS -> Color(0xFFFEF2F2)
                        QuestionCategory.TIES_214B -> Color(0xFFF0FDF4)
                        else -> Color(0xFFF0F9FF)
                    },
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = item.category.title,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (item.category) {
                            QuestionCategory.RED_FLAGS -> Color(0xFFDC2626)
                            QuestionCategory.TIES_214B -> Color(0xFF166534)
                            else -> Color(0xFF0369A1)
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Star else Icons.Default.Star,
                        contentDescription = "Bookmark",
                        tint = if (isBookmarked) Color(0xFFD97706) else Color(0xFFCBD5E1)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Main Question
            Text(
                text = item.question,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A),
                lineHeight = 21.sp
            )

            if (!isExpanded) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = item.shortSummary,
                    fontSize = 12.sp,
                    color = Color(0xFF64748B),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Tap to see Model Answer & Officer Intent",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF00A699)
                    )
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color(0xFF00A699),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Expanded Details
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Divider(color = Color(0xFFF1F5F9), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Model Answer Section
                    Surface(
                        color = Color(0xFFF0FDF4),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color(0xFF16A34A),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "RECOMMENDED MODEL ANSWER",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF15803D)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = item.sampleAnswer,
                                fontSize = 13.sp,
                                color = Color(0xFF14532D),
                                lineHeight = 19.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Officer Intent
                    Surface(
                        color = Color(0xFFF8FAFC),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = null,
                                    tint = Color(0xFF0284C7),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "WHAT THE OFFICER IS REALLY TESTING",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0369A1)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = item.officerIntent,
                                fontSize = 12.sp,
                                color = Color(0xFF334155),
                                lineHeight = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Red Flags & What NOT to say
                    Surface(
                        color = Color(0xFFFEF2F2),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = null,
                                    tint = Color(0xFFDC2626),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "TRAPS & WHAT NOT TO SAY",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFB91C1C)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            item.redFlags.forEach { flag ->
                                Row(
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text("• ", color = Color(0xFFDC2626), fontSize = 12.sp)
                                    Text(
                                        text = flag,
                                        fontSize = 12.sp,
                                        color = Color(0xFF7F1D1D),
                                        lineHeight = 17.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Quick Tips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = onToggleExpand) {
                            Text("Collapse", fontSize = 12.sp, color = Color(0xFF64748B))
                            Icon(
                                Icons.Default.KeyboardArrowUp,
                                contentDescription = null,
                                tint = Color(0xFF64748B),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GuidesTab(
    selectedGuide: VisaGuideItem?,
    onSelectGuide: (VisaGuideItem?) -> Unit
) {
    if (selectedGuide != null) {
        // Guide Detail View
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextButton(
                onClick = { onSelectGuide(null) },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("← Back to Guides List", fontWeight = FontWeight.Bold, color = Color(0xFF0A2540))
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Text(
                        text = selectedGuide.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0A2540)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${selectedGuide.category} • ${selectedGuide.readTime}",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )
                }

                item {
                    Surface(
                        color = Color(0xFFF0FDF4),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                "KEY TAKEAWAYS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF15803D)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            selectedGuide.keyPoints.forEach { point ->
                                Row(modifier = Modifier.padding(vertical = 3.dp)) {
                                    Text("✓ ", color = Color(0xFF16A34A), fontSize = 13.sp)
                                    Text(point, fontSize = 13.sp, color = Color(0xFF14532D))
                                }
                            }
                        }
                    }
                }

                items(selectedGuide.content) { paragraph ->
                    Text(
                        text = paragraph,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        color = Color(0xFF334155)
                    )
                }
            }
        }
    } else {
        // Guides List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Essential Embassy Guides",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0A2540)
                )
                Text(
                    text = "Master the core principles of U.S. visa law and consular psychology.",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            items(VisaQuestionsRepository.guides) { guide ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectGuide(guide) },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = guide.category.uppercase(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00A699)
                            )
                            Text(
                                text = guide.readTime,
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = guide.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = guide.summary,
                            fontSize = 13.sp,
                            color = Color(0xFF64748B),
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Read Guide →",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0A2540)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BookmarksTab(
    bookmarkedIds: List<String>,
    expandedQuestionId: String?,
    onToggleExpand: (String) -> Unit,
    onToggleBookmark: (String) -> Unit,
    onNavigateToQuestions: () -> Unit
) {
    val savedQuestions = remember(bookmarkedIds) {
        VisaQuestionsRepository.questions.filter { bookmarkedIds.contains(it.id) }
    }

    if (savedQuestions.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFCBD5E1),
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "No Saved Questions Yet",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF334155)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Tap the star on any question in the Questions tab to save it for quick review right before your embassy appointment.",
                    fontSize = 13.sp,
                    color = Color(0xFF64748B),
                    lineHeight = 18.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onNavigateToQuestions,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A2540))
                ) {
                    Text("Browse Questions")
                }
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Saved for Quick Review",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0A2540)
                        )
                        Text(
                            text = "${savedQuestions.size} questions saved offline",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }

            items(savedQuestions, key = { it.id }) { item ->
                QuestionCard(
                    item = item,
                    isExpanded = expandedQuestionId == item.id,
                    isBookmarked = true,
                    onToggleExpand = { onToggleExpand(item.id) },
                    onToggleBookmark = { onToggleBookmark(item.id) }
                )
            }
        }
    }
}
